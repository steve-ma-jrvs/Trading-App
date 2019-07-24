package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {

  private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);

  private final String TABLE_NAME = "position";
  private final String ID_COLUMN = "account_id";

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public PositionDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public List<Position> findByAccoundId(Integer accountID) {
    String selectSql = "select * from " + TABLE_NAME + " where account_id = ?";
    return jdbcTemplate
        .query(selectSql, BeanPropertyRowMapper.newInstance(Position.class), accountID);
  }

  public Long findByIdAndTicker(Integer accountID, String ticker) {
    String selectSql = "select position from " + TABLE_NAME + " where account_id=? and ticker=?";
    Long position = 0L;
    try {
      position = jdbcTemplate.queryForObject(selectSql, Long.class, accountID, ticker);
    } catch (EmptyResultDataAccessException e) {
      logger.debug(String
          .format("select position from position accountId=%s and ticker=%s", accountID, ticker)
      );
    }
    return position;
  }
}
