package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends JdbcCrudDao<Account, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

  private final String TABLE_NAME = "account";
  private final String ID_COLUMN = "id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleInsert;

  @Autowired
  public AccountDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    simpleInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
        .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleInsert;
  }

  @Override
  public String getTableName() {
    return TABLE_NAME;
  }

  @Override
  public String getIdName() {
    return ID_COLUMN;
  }

  @Override
  Class getEntityClass() {
    return Account.class;
  }

  public Account findByTraderId(Integer traderId) {
    return super.findById("trader_id", traderId, false, getEntityClass());
  }

  public Account findByTraderIdForUpdate(Integer traderId) {
    return super.findById("trader_id", traderId, true, getEntityClass());
  }

  /**
   * @return updated account or null if id not found
   */
  public Account updateAmountById(Integer id, Double amount) {
    if (super.existsById(id)) {
      String sql = "UPDATE " + TABLE_NAME + " SET amount=? WHERE id=?";
      int row = jdbcTemplate.update(sql, amount, id);
      logger.debug("Update amount row=" + row);
      if (row != 1) {
        throw new IncorrectResultSizeDataAccessException(1, row);
      }
      return findById(id);
    }
    return null;
  }
}
