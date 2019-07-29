package ca.jrvs.apps.trading.model.domain;


public class Quote implements Entity<String>{

  // P-key
  private String ticker;
  private double lastPrice;
  private double bidPrice;
  private int bidSize;
  private double askPrice;
  private int askSize;

  @Override
  public String getId() {
    return ticker.toUpperCase();
  }

  @Override
  public void setId(String id) {
    this.ticker = id;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public double getLastPrice() {
    return lastPrice;
  }

  public void setLastPrice(double lastPrice) {
    this.lastPrice = lastPrice;
  }

  public double getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(double bidPrice) {
    this.bidPrice = bidPrice;
  }

  public int getBidSize() {
    return bidSize;
  }

  public void setBidSize(int bidSize) {
    this.bidSize = bidSize;
  }

  public double getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(double askPrice) {
    this.askPrice = askPrice;
  }

  public int getAskSize() {
    return askSize;
  }

  public void setAskSize(int askSize) {
    this.askSize = askSize;
  }

}
