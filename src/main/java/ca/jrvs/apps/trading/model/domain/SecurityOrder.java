package ca.jrvs.apps.trading.model.domain;

public class SecurityOrder implements Entity<Integer> {

  // P-key
  private Integer id;
  // F-key -> account
  private Integer account_id;
  private String status;
  // F-key -> quote
  private String ticker;
  private int size;
  private double price;
  private String notes;

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getAccount_id() {
    return account_id;
  }

  public void setAccount_id(Integer account_id) {
    this.account_id = account_id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @Override
  public String toString() {
    return "SecurityOrder{" +
        "id=" + id +
        ", account_id=" + account_id +
        ", status='" + status + '\'' +
        ", ticker='" + ticker + '\'' +
        ", size=" + size +
        ", price=" + price +
        ", notes='" + notes + '\'' +
        '}';
  }
}
