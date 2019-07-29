package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

  private AccountDao accountDao;
  private SecurityOrderDao securityOrderDao;
  private QuoteDao quoteDao;
  private PositionDao positionDao;

  @Autowired
  public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao,
      QuoteDao quoteDao, PositionDao positionDao) {
    this.accountDao = accountDao;
    this.securityOrderDao = securityOrderDao;
    this.quoteDao = quoteDao;
    this.positionDao = positionDao;
  }

  /**
   * Execute a market order
   *
   * - validate the order (e.g. size, and ticker) - Create a securityOrder (for security_order
   * table) - Handle buy or sell order - buy order : check account balance - sell order: check
   * position for the ticker/symbol - (please don't forget to update securityOrder.status) - Save
   * and return securityOrder
   *
   * NOTE: you will need to some helper methods (protected or private)
   *
   * @param orderDto market order
   * @return SecurityOrder from security_order table
   * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
   * @throws IllegalArgumentException for invalid input
   */
  public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {
    if (orderDto.getSize() == null || orderDto.getSize() == 0) {
      throw new IllegalArgumentException("Invalid order size");
    }

    Quote quote = quoteDao.findById(orderDto.getTicker());
    if (quote == null) {
      throw new IllegalArgumentException(orderDto.getTicker() + " is not in the database");
    }

    SecurityOrder securityOrder = new SecurityOrder();
    securityOrder.setAccount_id(orderDto.getAccountID());
    securityOrder.setTicker(orderDto.getTicker());
    securityOrder.setSize(orderDto.getSize());
    Account account = accountDao.findByIdForUpdate(orderDto.getAccountID());

    // Handle buy or sell order
    if (orderDto.getSize() > 0) {
      securityOrder.setPrice(quote.getAskPrice());
      handleBuyOrder(securityOrder, account);
    } else {
      securityOrder.setPrice(quote.getBidPrice());
      handleSellOrder(securityOrder, account);
    }

    return securityOrderDao.save(securityOrder);
  }


  protected void handleBuyOrder(SecurityOrder securityOrder, Account account) {
    Double amount = account.getAmount();
    Double buyPower = securityOrder.getPrice() * securityOrder.getSize();
    if (amount > buyPower) {
      securityOrder.setStatus("FILLED");
      securityOrder.setNotes(null);
      accountDao.updateAmountById(account.getId(), amount - buyPower);
    } else {
      securityOrder.setStatus("CANCELED");
      securityOrder.setNotes("Insufficient fund. Requires buypower: " + buyPower);
    }
  }

  protected void handleSellOrder(SecurityOrder securityOrder, Account account) {
    Double amount = account.getAmount();
    Double sellPrice = securityOrder.getPrice() * securityOrder.getSize();
    Long position = positionDao.findByIdAndTicker(account.getId(), securityOrder.getTicker());
    if (position >= Math.abs(securityOrder.getSize())) {
      securityOrder.setStatus("FILLED");
      securityOrder.setNotes(null);
      accountDao.updateAmountById(account.getId(), amount - sellPrice);
    } else {
      securityOrder.setStatus("CANCELED");
      securityOrder.setNotes("Insufficient position.");
    }
  }
}
