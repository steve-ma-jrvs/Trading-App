package ca.jrvs.apps.trading.model.domain;

public class Account implements Entity<Integer> {

  // P-key
  private Integer id;
  // F-key -> trader
  private Integer trader_id;
  private double amount;

  public Account(Integer id, Integer trader_id, double amount) {
    this.id = id;
    this.trader_id = trader_id;
    this.amount = amount;
  }

  public Account(Integer trader_id) {
    this.trader_id = trader_id;
    this.amount = 0.0;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getTrader_id() {
    return trader_id;
  }

  public void setTrader_id(Integer trader_id) {
    this.trader_id = trader_id;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }
}
