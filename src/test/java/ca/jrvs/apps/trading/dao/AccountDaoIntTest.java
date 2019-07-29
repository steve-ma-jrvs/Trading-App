package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.model.domain.Account;
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
public class AccountDaoIntTest {

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  private Account savedAccount;
  private Trader savedTrader;


  @Before
  public void InsertOne() {
    savedTrader = new Trader();
    savedTrader.setCountry("CANADA");
    savedTrader.setDob(new Date(System.currentTimeMillis()));
    savedTrader.setEmail("steve@jrvs.ca");
    savedTrader.setFirst_name("steve");
    savedTrader.setLast_name("ma");
    savedTrader = traderDao.save(savedTrader);

    savedAccount = new Account(savedTrader.getId());
    savedAccount = accountDao.save(savedAccount);
  }

  @After
  public void DeleteOne() {
    accountDao.deleteById(savedAccount.getId());
    assertFalse(accountDao.existsById(1));
    traderDao.deleteById(savedTrader.getId());
    assertFalse(traderDao.existsById(1));
  }

  @Test
  public void findByTraderId() {
    Account account = accountDao.findByTraderId(1);
    assertSame(account.getId(), savedAccount.getId());
  }

  @Test
  public void findById() {
    Account account = accountDao.findById(1);
    assertSame(account.getTrader_id(), savedAccount.getTrader_id());
  }

  @Test
  public void updateAmountById() {
    Account account = accountDao.updateAmountById(1, 50.0);
    savedAccount = accountDao.findByIdForUpdate(1);
    assertTrue(account.getAmount() == savedAccount.getAmount());
  }
}