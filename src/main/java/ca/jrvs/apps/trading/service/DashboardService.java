package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.SecurityRow;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  private TraderDao traderDao;
  private PositionDao positionDao;
  private AccountDao accountDao;
  private QuoteDao quoteDao;

  @Autowired
  public DashboardService(TraderDao traderDao, PositionDao positionDao, AccountDao accountDao,
      QuoteDao quoteDao) {
    this.traderDao = traderDao;
    this.positionDao = positionDao;
    this.accountDao = accountDao;
    this.quoteDao = quoteDao;
  }

  /**
   * Create and return a traderAccountView by trader ID
   * - get trader account by id
   * - get trader info by id
   * - create and return a traderAccountView
   *
   * @param traderId trader ID
   * @return traderAccountView
   * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public TraderAccountView getTraderAccount(Integer traderId) {
    TraderAccountView traderAccountView = new TraderAccountView();
    traderAccountView.setTrader(traderDao.findById(traderId));
    traderAccountView.setAccount(accountDao.findByTraderId(traderId));
    return traderAccountView;
  }

  /**
   * Create and return portfolioView by trader ID
   * - get account by trader id
   * - get positions by account id
   * - create and return a portfolioView
   *
   * @param traderId
   * @return portfolioView
   * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public PortfolioView getProfileViewByTraderId(Integer traderId) {
    PortfolioView portfolioView = new PortfolioView();
    Account account = accountDao.findByTraderId(traderId);
    List<Position> positions = positionDao.findByAccoundId(account.getId());
    List<SecurityRow> securityRows = new ArrayList<>();
    for (Position position : positions) {
      SecurityRow securityRow = new SecurityRow(position, quoteDao.findById(position.getTicker()),
          position.getTicker());
      securityRows.add(securityRow);
    }
    portfolioView.setSecurityRows(securityRows);
    return portfolioView;
  }
}
