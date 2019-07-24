package ca.jrvs.apps.trading.util;

import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import com.fasterxml.jackson.core.JsonProcessingException;

public class QuoteUtil {

  public static void printIexQuote(IexQuote iexQuote) {
    try {
      System.out.println(JsonUtil.toPrettyJson(iexQuote));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Unable to convert tweet object to string", e);
    }
  }

  public static void printQuote(Quote quote) {
    try {
      System.out.println(JsonUtil.toPrettyJson(quote));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Unable to convert tweet object to string", e);
    }
  }
}
