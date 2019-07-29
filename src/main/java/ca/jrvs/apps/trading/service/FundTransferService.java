package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.model.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FundTransferService {

  private AccountDao accountDao;

  @Autowired
  public FundTransferService(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  /**
   * Deposit a fund to the account which is associated with the traderId
   * - validate user input
   * - account = accountDao.findByTraderId
   * - accountDao.updateAmountById
   *
   * @param traderId trader id
   * @param fund found amount (can't be 0)
   * @return updated Account object
   * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public Account deposit(Integer traderId, Double fund) {
    if (!accountDao.existsById("trader_id", traderId)) {
      throw new IllegalArgumentException("traderId not found" + traderId);
    }
    if (fund <= 0) {
      throw new IllegalArgumentException("fund can't be 0 or less");
    }
    Account account = accountDao.findByTraderIdForUpdate(traderId);
    Integer accountID = account.getId();
    Double accountAmount = account.getAmount();
    accountDao.updateAmountById(accountID, accountAmount + fund);
    return accountDao.findByIdForUpdate(accountID);
  }

  /**
   * Withdraw a fund from the account which is associated with the traderId
   *
   * - validate user input
   * - account = accountDao.findByTraderId
   * - accountDao.updateAmountById
   *
   * @param traderId trader ID
   * @param fund amount can't be 0
   * @return updated Account object
   * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public Account withdraw(Integer traderId, Double fund) {
    if (!accountDao.existsById("traderId", traderId)) {
      throw new IllegalArgumentException("traderId not found" + traderId);
    }
    if (fund <= 0) {
      throw new IllegalArgumentException("fund can't be 0 or less");
    }
    Account account = accountDao.findByTraderIdForUpdate(traderId);
    Integer accountID = account.getId();
    Double accountAmount = account.getAmount();
    if (fund > accountAmount) {
      throw new IllegalArgumentException("Account amount is not enough");
    }
    accountDao.updateAmountById(accountID, accountAmount - fund);
    return accountDao.findByIdForUpdate(accountID);
  }
}
