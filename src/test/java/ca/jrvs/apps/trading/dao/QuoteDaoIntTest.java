package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Arrays;
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
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao quoteDao;

  private Quote savedQuote;

  @Before
  public void insertOne() {
    savedQuote = new Quote();
    savedQuote.setTicker("aapl");
    savedQuote.setLastPrice(200.0);
    savedQuote.setAskPrice(100.0);
    savedQuote.setAskSize(100);
    savedQuote.setBidPrice(100.0);
    savedQuote.setBidSize(100);
    savedQuote = quoteDao.save(savedQuote);
  }

  @After
  public void deleteOne() {
    quoteDao.deleteById(savedQuote.getTicker());
    assertFalse(quoteDao.existsById("aapl"));
  }

  @Test
  public void findById() {
    Quote quote = quoteDao.findById(savedQuote.getTicker());
    String expected = "AAPL";
    assertTrue(savedQuote.getId().equals(expected));
  }

  @Test
  public void existsById() {
    assertTrue(quoteDao.existsById(savedQuote.getTicker()));
    assertFalse(quoteDao.existsById("fb"));
  }

  @Test
  public void deleteById() {
    quoteDao.deleteById(savedQuote.getTicker());
    assertFalse(quoteDao.existsById(savedQuote.getTicker()));
  }

  @Test
  public void update() {
    Quote savedQuote1 = new Quote();
    savedQuote1.setTicker("aapl");
    savedQuote1.setLastPrice(100.0);
    savedQuote1.setAskPrice(100.0);
    savedQuote1.setAskSize(100);
    savedQuote1.setBidPrice(100.0);
    savedQuote1.setBidSize(100);

    Quote savedQuote2 = new Quote();
    savedQuote2.setTicker("aapl");
    savedQuote2.setLastPrice(25.0);
    savedQuote2.setAskPrice(50.0);
    savedQuote2.setAskSize(50);
    savedQuote2.setBidPrice(20.0);
    savedQuote2.setBidSize(40);

    quoteDao.update(Arrays.asList(savedQuote1, savedQuote2));
    assertTrue(25.0 == quoteDao.findById("aapl").getLastPrice());

    Quote savedQuote3 = new Quote();
    savedQuote3.setTicker("fb");
    savedQuote3.setLastPrice(100.0);
    savedQuote3.setAskPrice(100.0);
    savedQuote3.setAskSize(100);
    savedQuote3.setBidPrice(100.0);
    savedQuote3.setBidSize(100);

    try {
      quoteDao.update(Arrays.asList(savedQuote1, savedQuote3));
      fail();
    } catch (ResourceNotFoundException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }

  }
}