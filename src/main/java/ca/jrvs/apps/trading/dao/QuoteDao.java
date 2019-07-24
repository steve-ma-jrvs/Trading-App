package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

  private final String TABLE_NAME = "quote";
  //private final String ID_COLUMN = "ticker";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleInsert;

  @Autowired
  public QuoteDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
  }

  @Override
  public Quote save(Quote quote) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
    int row = simpleInsert.execute(parameterSource);
    if (row != 1) {
      throw new IncorrectResultSizeDataAccessException("Fail to insert", 1, row);
    }
    return quote;
  }

  @Override
  public Quote findById(String ticker) {
    if (ticker == null) {
      throw new IllegalArgumentException("ID can't be null");
    }
    Quote quote = null;
    try {
      quote = jdbcTemplate
          .queryForObject("select * from " + TABLE_NAME + " where ticker = ?",
              BeanPropertyRowMapper.newInstance(Quote.class), ticker);
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Can't find trader id:" + ticker, e);
    }
    return quote;
  }

  public List<Quote> findAll() {
    String selectAll = "select * from " + TABLE_NAME;
    return jdbcTemplate
        .query(selectAll, BeanPropertyRowMapper.newInstance(Quote.class));
  }

  @Override
  public boolean existsById(String ticker) {
    if (ticker == null) {
      throw new IllegalArgumentException("ID can't be null");
    }

    Integer count = jdbcTemplate
        .queryForObject("select count(*) from " + TABLE_NAME + " where ticker = ?",
            Integer.class, ticker);

    return count > 0;
  }

  @Override
  public void deleteById(String ticker) {
    if (ticker == null) {
      throw new IllegalArgumentException("ID can't be null");
    }
    jdbcTemplate.update("delete from " + TABLE_NAME + " where ticker = ?", ticker);
  }

  public void update(List<Quote> quotes) {
    String updateSQL = "update " + TABLE_NAME + " set last_price=?, bid_price=?, bid_size=?, "
        + "ask_price=?, ask_size=? where ticker = ?";
    List<Object[]> batch = new ArrayList<>();
    quotes.forEach(quote -> {
      if (!existsById(quote.getTicker())) {
        throw new ResourceNotFoundException("Ticker not found: " + quote.getId());
      }
      Object[] values = new Object[]{quote.getLastPrice(), quote.getBidPrice(), quote.getBidSize(),
          quote.getAskPrice(), quote.getAskSize(), quote.getTicker()};
      batch.add(values);
    });

    int[] rows = jdbcTemplate.batchUpdate(updateSQL, batch);
    int totalRow = Arrays.stream(rows).sum();
    if (totalRow != quotes.size()) {
      throw new IncorrectResultSizeDataAccessException("Number of rows ", quotes.size(), totalRow);
    }
  }
}
