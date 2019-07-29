package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDataDao {

  //URI constants
  private static final String API_BASE_URI = "https://cloud.iexapis.com/stable/stock/";
  private static final String API_BASE_URI_BATCH = "https://cloud.iexapis.com/stable/stock/market/batch?symbols";
  private static final String QUOTE = "types=quote";
  private static final String SHOW_PATH = "/quote";
  private static final String IEX_PUB_TOKEN = System.getenv("IEX_PUB_TOKEN");
  private static final String TOKEN_PATH = "token=";

  //Response code
  private static final int HTTP_OK = 200;

  //URI symbols
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";
  private static final String SEPARATOR = ",";

  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);

  private HttpClientConnectionManager httpClientConnectionManager;


  @Autowired
  public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager) {
    this.httpClientConnectionManager = httpClientConnectionManager;
  }


  public List<IexQuote> findIexQuoteByTicker(List<String> tickers) {
    //Construct URI
    URI uri;
    try {
      uri = getShowUri(tickers);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Unable to construct URI", e);
    }
    logger.info("Get URI" + uri);

    //Convert JsonStr to JsonObject
    JSONObject IexQuotesJson = new JSONObject(HttpGet(uri));

    if (IexQuotesJson.length() != tickers.size()) {
      throw new IllegalArgumentException("Invalid tickers");
    }

    List<IexQuote> iexQuoteList = new ArrayList<>();
    IexQuotesJson.keys().forEachRemaining(ticker -> {
          try {
            String quoteStr = ((JSONObject) IexQuotesJson.get(ticker)).get("quote").toString();
            IexQuote iexQuote = JsonUtil.toObjectFromJson(quoteStr, IexQuote.class);
            iexQuoteList.add(iexQuote);
          } catch (IOException e) {
            throw new DataRetrievalFailureException(
                "Unable parse response" + IexQuotesJson.get(ticker), e
            );
          }
        }
    );

    return iexQuoteList;
  }

  protected URI getShowUri(List<String> tickers) throws URISyntaxException {
    StringBuilder sb = new StringBuilder();
    sb.append(API_BASE_URI_BATCH)
        .append(EQUAL)
        .append(tickers.stream()
            .map(String::toLowerCase)
            .collect(Collectors.joining(SEPARATOR)))
        .append(AMPERSAND)
        .append(QUOTE)
        .append(AMPERSAND)
        .append(TOKEN_PATH)
        .append(IEX_PUB_TOKEN);

    return new URI(sb.toString());
  }

  public IexQuote findIexQuoteByTicker(String ticker) {
    //Construct URI
    URI uri;
    try {
      uri = getShowUri(ticker);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Unable to construct URI", e);
    }

    IexQuote iexQuote;
    try {
      iexQuote = (IexQuote) JsonUtil.toObjectFromJson(HttpGet(uri), IexQuote.class);
    } catch (IOException e) {
      throw new RuntimeException("Unable to convert JSON str to Object", e);
    }
    return iexQuote;
  }


  protected URI getShowUri(String ticker) throws URISyntaxException {
    StringBuilder sb = new StringBuilder();
    sb.append(API_BASE_URI)
        .append(ticker)
        .append(SHOW_PATH)
        .append(QUERY_SYM)
        .append(TOKEN_PATH)
        .append(IEX_PUB_TOKEN);

    return new URI(sb.toString());
  }


  protected String HttpGet(URI uri) {
    try (CloseableHttpClient httpClient = getHttpClient()) {
      HttpGet httpGet = new HttpGet(uri);
      try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
        switch (response.getStatusLine().getStatusCode()) {
          case 200:
            String jsonStr;
            try {
              jsonStr = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
              throw new RuntimeException("Failed to convert entity to String", e);
            }
            return jsonStr;
          case 400:
            throw new RuntimeException("Not found");
          default:
            throw new DataRetrievalFailureException(
                "Unexpected status" + response.getStatusLine().getStatusCode());
        }
      }
    } catch (IOException e) {
      throw new DataRetrievalFailureException("HttpGet error", e);
    }
  }


  private CloseableHttpClient getHttpClient() {
    return HttpClients.custom()
        .setConnectionManager(httpClientConnectionManager)
        .setConnectionManagerShared(true)
        .build();
  }

}
