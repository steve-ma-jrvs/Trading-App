package ca.jrvs.apps.trading.model.view;

import java.util.List;

public class PortfolioView {

  private List<SecurityRow> securityRows;

  public List<SecurityRow> getSecurityRows() {
    return securityRows;
  }

  public void setSecurityRows(List<SecurityRow> securityRows) {
    this.securityRows = securityRows;
  }
}


