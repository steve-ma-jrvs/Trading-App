package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Arrays;
import java.util.List;
import org.apache.http.conn.HttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

public class MarketDataDaoIntTest {

  private MarketDataDao dao;

  @Before
  public void setUp(){
    TestConfig testConfig = new TestConfig();
    HttpClientConnectionManager cm = testConfig.httpClientConnectionManager();
    dao = new MarketDataDao(cm);
  }

  @Test
  public void findIexQuoteByTicker() throws JsonProcessingException {
    String ticker = "AAPL";
    IexQuote iexQuote = dao.findIexQuoteByTicker(ticker);
    System.out.println(JsonUtil.toPrettyJson(iexQuote));
    assertEquals(ticker, iexQuote.getSymbol());
  }

  @Test
  public void testFindIexQuoteByTicker() {
    List<IexQuote> quoteList = dao.findIexQuoteByTicker(Arrays.asList("AAPL", "FB"));
    assertEquals(2, quoteList.size());
    assertEquals("AAPL", quoteList.get(0).getSymbol());

    try {
      dao.findIexQuoteByTicker(Arrays.asList("AAPL", "FB2"));
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }
}