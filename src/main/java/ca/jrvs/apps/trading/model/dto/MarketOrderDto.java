package ca.jrvs.apps.trading.model.dto;

public class MarketOrderDto {

  private Integer accountID;
  private String ticker;
  private Integer size;

  public MarketOrderDto() {
  }

  public MarketOrderDto(Integer accountID, String ticker, Integer size) {
    this.accountID = accountID;
    this.ticker = ticker;
    this.size = size;
  }

  public Integer getAccountID() {
    return accountID;
  }

  public void setAccountID(Integer accountID) {
    this.accountID = accountID;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }
}
