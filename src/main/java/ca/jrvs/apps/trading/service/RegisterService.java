package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.util.StringUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

  private TraderDao traderDao;
  private AccountDao accountDao;
  private PositionDao positionDao;
  private SecurityOrderDao securityOrderDao;

  @Autowired
  public RegisterService(TraderDao traderDao, AccountDao accountDao,
      PositionDao positionDao, SecurityOrderDao securityOrderDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.positionDao = positionDao;
    this.securityOrderDao = securityOrderDao;
  }

  /**
   * Create a new trader and initialize a new account with 0 amount.
   * - validate user input (all fields must be non empty)
   * - create a trader
   * - create an account
   * - create, setup, and return a new traderAccountView
   *
   * @param trader trader info
   * @return traderAccountView
   * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public TraderAccountView createTraderAndAccount(Trader trader) {
    String[] traderInfo = new String[]{trader.getCountry(), trader.getEmail(),
        trader.getFirst_name(), trader.getFirst_name(), trader.getDob().toString()};
    if (StringUtil.isEmpty(traderInfo)) {
      throw new IllegalArgumentException("Trader info has empty fields.");
    }
    Trader newTrader = traderDao.save(trader);
    Account account = new Account(newTrader.getId());
    Account newAccount = accountDao.save(account);
    return new TraderAccountView(newTrader, newAccount);
  }

  /**
   * A trader can be deleted iff no open position and no cash balance.
   * - validate traderID
   * - get trader account by traderId and check account balance
   * - get positions by accountId and check positions
   * - delete all securityOrders, account, trader (in this order)
   *
   * @param traderId
   * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public void deleteTraderById(Integer traderId) {
    if (!traderDao.existsById(traderId)) {
      throw new IllegalArgumentException("TraderID " + traderId + " doesn't exist.");
    }

    Account account = accountDao.findByTraderId(traderId);
    int accountID = account.getId();

    if (account.getAmount() != 0) {
      throw new IllegalArgumentException("Account amount is not 0, can't delete account");
    }

    List<Position> positions = positionDao.findByAccoundId(accountID);
    if (positions.stream().anyMatch(position -> position.getPosition() != 0)) {
      throw new IllegalArgumentException("Can't delete account with open position");
    }

    securityOrderDao.deleteByAccountId(accountID);
    accountDao.deleteById(accountID);
    traderDao.deleteById(traderId);
  }
}
