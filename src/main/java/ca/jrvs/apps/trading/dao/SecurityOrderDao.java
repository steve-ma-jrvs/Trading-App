package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(SecurityOrderDao.class);

  private final String TABLE_NAME = "security_order";
  private final String ID_COLUMN = "id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleInsert;

  @Autowired
  public SecurityOrderDao(DataSource dataSource) {
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
    return SecurityOrder.class;
  }

  public SecurityOrder findByAccountId(Integer account_id) {
    return super.findById("account_id", account_id, false, getEntityClass());
  }

  public SecurityOrder findByAccountIdForUpdate(Integer account_id) {
    return super.findById("account_id", account_id, true, getEntityClass());
  }

  public void deleteByAccountId(Integer account_id) {
    super.deleteById("account_id", account_id);
  }
}
