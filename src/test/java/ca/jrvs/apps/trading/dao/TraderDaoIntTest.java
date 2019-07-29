package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderDaoIntTest {

  @Autowired
  private TraderDao traderDao;

  private Trader savedTrader;

  @Before
  public void insertOne() {
    savedTrader = new Trader();
    savedTrader.setCountry("CANADA");
    savedTrader.setDob(new Date(System.currentTimeMillis()));
    savedTrader.setEmail("steve@jrvs.ca");
    savedTrader.setFirst_name("steve");
    savedTrader.setLast_name("ma");
    savedTrader = traderDao.save(savedTrader);
  }

  @After
  public void deleteOne() {
    traderDao.deleteById(savedTrader.getId());
    assertFalse(traderDao.existsById(1));
  }

  @Test
  public void findById() {
    Trader trader = traderDao.findById(savedTrader.getId());
    assertSame(trader.getId(), savedTrader.getId());
  }

  @Test
  public void existsById() {
    assertTrue(traderDao.existsById(savedTrader.getId()));
    assertFalse(traderDao.existsById(-1));
  }

  @Test
  public void deleteById() {
    traderDao.deleteById(1);
    assertFalse(traderDao.existsById(1));
    traderDao.deleteById(-1);
  }
}