package ca.jrvs.apps.trading.model.domain;

public class Position {

  private Integer account_id;
  private String ticker;
  private Long position;

  public Integer getAccount_id() {
    return account_id;
  }

  public void setAccount_id(Integer account_id) {
    this.account_id = account_id;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public Long getPosition() {
    return position;
  }

  public void setPosition(Long position) {
    this.position = position;
  }
}
