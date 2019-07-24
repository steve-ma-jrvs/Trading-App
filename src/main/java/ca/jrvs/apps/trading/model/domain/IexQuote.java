package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "symbol",
    "companyName",
    "calculationPrice",
    "open",
    "openTime",
    "close",
    "closeTime",
    "high",
    "low",
    "latestPrice",
    "latestSource",
    "latestTime",
    "latestUpdate",
    "latestVolume",
    "iexRealtimePrice",
    "iexRealtimeSize",
    "iexLastUpdated",
    "delayedPrice",
    "delayedPriceTime",
    "extendedPrice",
    "extendedChange",
    "extendedChangePercent",
    "extendedPriceTime",
    "previousClose",
    "change",
    "changePercent",
    "iexMarketPercent",
    "iexVolume",
    "avgTotalVolume",
    "iexBidPrice",
    "iexBidSize",
    "iexAskPrice",
    "iexAskSize",
    "marketCap",
    "peRatio",
    "week52High",
    "week52Low",
    "ytdChange",
    "lastTradeTime"
})
public class IexQuote {

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("companyName")
  private String companyName;

  @JsonProperty("calculationPrice")
  private String calculationPrice;

  @JsonProperty("open")
  private double open;

  @JsonProperty("openTime")
  private long openTime;

  @JsonProperty("close")
  private double close;

  @JsonProperty("closeTime")
  private long closeTime;

  @JsonProperty("high")
  private double high;

  @JsonProperty("low")
  private double low;

  @JsonProperty("latestPrice")
  private double latestPrice;

  @JsonProperty("latestSource")
  private String latestSource;

  @JsonProperty("latestTime")
  private String latestTime;

  @JsonProperty("latestUpdate")
  private long latestUpdate;

  @JsonProperty("latestVolume")
  private long latestVolume;

  @JsonProperty("iexRealtimePrice")
  private double iexRealtimePrice;

  @JsonProperty("iexRealtimeSize")
  private int iexRealtimeSize;

  @JsonProperty("iexLastUpdated")
  private long iexLastUpdated;

  @JsonProperty("delayedPrice")
  private double delayedPrice;

  @JsonProperty("delayedPriceTime")
  private long delayedPriceTime;

  @JsonProperty("extendedPrice")
  private double extendedPrice;

  @JsonProperty("extendedChange")
  private double extendedChange;

  @JsonProperty("extendedChangePercent")
  private double extendedChangePercent;

  @JsonProperty("extendedPriceTime")
  private long extendedPriceTime;

  @JsonProperty("previousClose")
  private double previousClose;

  @JsonProperty("change")
  private double change;

  @JsonProperty("changePercent")
  private double changePercent;

  @JsonProperty("iexMarketPercent")
  private double iexMarketPercent;

  @JsonProperty("iexVolume")
  private int iexVolume;

  @JsonProperty("avgTotalVolume")
  private int avgTotalVolume;

  @JsonProperty("iexBidPrice")
  private double iexBidPrice;

  @JsonProperty("iexBidSize")
  private int iexBidSize;

  @JsonProperty("iexAskPrice")
  private double iexAskPrice;

  @JsonProperty("iexAskSize")
  private int iexAskSize;

  @JsonProperty("marketCap")
  private long marketCap;

  @JsonProperty("peRatio")
  private double peRatio;

  @JsonProperty("week52High")
  private double week52High;

  @JsonProperty("week52Low")
  private int week52Low;

  @JsonProperty("ytdChange")
  private double ytdChange;

  @JsonProperty("lastTradeTime")
  private long lastTradeTime;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCalculationPrice() {
    return calculationPrice;
  }

  public void setCalculationPrice(String calculationPrice) {
    this.calculationPrice = calculationPrice;
  }

  public double getOpen() {
    return open;
  }

  public void setOpen(double open) {
    this.open = open;
  }

  public long getOpenTime() {
    return openTime;
  }

  public void setOpenTime(long openTime) {
    this.openTime = openTime;
  }

  public double getClose() {
    return close;
  }

  public void setClose(double close) {
    this.close = close;
  }

  public long getCloseTime() {
    return closeTime;
  }

  public void setCloseTime(long closeTime) {
    this.closeTime = closeTime;
  }

  public double getHigh() {
    return high;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  public double getLow() {
    return low;
  }

  public void setLow(double low) {
    this.low = low;
  }

  public double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(double latestPrice) {
    this.latestPrice = latestPrice;
  }

  public String getLatestSource() {
    return latestSource;
  }

  public void setLatestSource(String latestSource) {
    this.latestSource = latestSource;
  }

  public String getLatestTime() {
    return latestTime;
  }

  public void setLatestTime(String latestTime) {
    this.latestTime = latestTime;
  }

  public long getLatestUpdate() {
    return latestUpdate;
  }

  public void setLatestUpdate(long latestUpdate) {
    this.latestUpdate = latestUpdate;
  }

  public long getLatestVolume() {
    return latestVolume;
  }

  public void setLatestVolume(long latestVolume) {
    this.latestVolume = latestVolume;
  }

  public double getIexRealtimePrice() {
    return iexRealtimePrice;
  }

  public void setIexRealtimePrice(double iexRealtimePrice) {
    this.iexRealtimePrice = iexRealtimePrice;
  }

  public int getIexRealtimeSize() {
    return iexRealtimeSize;
  }

  public void setIexRealtimeSize(int iexRealtimeSize) {
    this.iexRealtimeSize = iexRealtimeSize;
  }

  public long getIexLastUpdated() {
    return iexLastUpdated;
  }

  public void setIexLastUpdated(long iexLastUpdated) {
    this.iexLastUpdated = iexLastUpdated;
  }

  public double getDelayedPrice() {
    return delayedPrice;
  }

  public void setDelayedPrice(double delayedPrice) {
    this.delayedPrice = delayedPrice;
  }

  public long getDelayedPriceTime() {
    return delayedPriceTime;
  }

  public void setDelayedPriceTime(long delayedPriceTime) {
    this.delayedPriceTime = delayedPriceTime;
  }

  public double getExtendedPrice() {
    return extendedPrice;
  }

  public void setExtendedPrice(double extendedPrice) {
    this.extendedPrice = extendedPrice;
  }

  public double getExtendedChange() {
    return extendedChange;
  }

  public void setExtendedChange(double extendedChange) {
    this.extendedChange = extendedChange;
  }

  public double getExtendedChangePercent() {
    return extendedChangePercent;
  }

  public void setExtendedChangePercent(double extendedChangePercent) {
    this.extendedChangePercent = extendedChangePercent;
  }

  public long getExtendedPriceTime() {
    return extendedPriceTime;
  }

  public void setExtendedPriceTime(long extendedPriceTime) {
    this.extendedPriceTime = extendedPriceTime;
  }

  public double getPreviousClose() {
    return previousClose;
  }

  public void setPreviousClose(double previousClose) {
    this.previousClose = previousClose;
  }

  public double getChange() {
    return change;
  }

  public void setChange(double change) {
    this.change = change;
  }

  public double getChangePercent() {
    return changePercent;
  }

  public void setChangePercent(double changePercent) {
    this.changePercent = changePercent;
  }

  public double getIexMarketPercent() {
    return iexMarketPercent;
  }

  public void setIexMarketPercent(double iexMarketPercent) {
    this.iexMarketPercent = iexMarketPercent;
  }

  public int getIexVolume() {
    return iexVolume;
  }

  public void setIexVolume(int iexVolume) {
    this.iexVolume = iexVolume;
  }

  public int getAvgTotalVolume() {
    return avgTotalVolume;
  }

  public void setAvgTotalVolume(int avgTotalVolume) {
    this.avgTotalVolume = avgTotalVolume;
  }

  public double getIexBidPrice() {
    return iexBidPrice;
  }

  public void setIexBidPrice(double iexBidPrice) {
    this.iexBidPrice = iexBidPrice;
  }

  public int getIexBidSize() {
    return iexBidSize;
  }

  public void setIexBidSize(int iexBidSize) {
    this.iexBidSize = iexBidSize;
  }

  public double getIexAskPrice() {
    return iexAskPrice;
  }

  public void setIexAskPrice(double iexAskPrice) {
    this.iexAskPrice = iexAskPrice;
  }

  public int getIexAskSize() {
    return iexAskSize;
  }

  public void setIexAskSize(int iexAskSize) {
    this.iexAskSize = iexAskSize;
  }

  public long getMarketCap() {
    return marketCap;
  }

  public void setMarketCap(long marketCap) {
    this.marketCap = marketCap;
  }

  public double getPeRatio() {
    return peRatio;
  }

  public void setPeRatio(double peRatio) {
    this.peRatio = peRatio;
  }

  public double getWeek52High() {
    return week52High;
  }

  public void setWeek52High(double week52High) {
    this.week52High = week52High;
  }

  public int getWeek52Low() {
    return week52Low;
  }

  public void setWeek52Low(int week52Low) {
    this.week52Low = week52Low;
  }

  public double getYtdChange() {
    return ytdChange;
  }

  public void setYtdChange(double ytdChange) {
    this.ytdChange = ytdChange;
  }

  public long getLastTradeTime() {
    return lastTradeTime;
  }

  public void setLastTradeTime(long lastTradeTime) {
    this.lastTradeTime = lastTradeTime;
  }
}
