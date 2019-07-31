package ca.jrvs.apps.trading;


import ca.jrvs.apps.trading.controller.TraderController;
import ca.jrvs.apps.trading.service.QuoteService;
import java.util.Arrays;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {
    JdbcTemplateAutoConfiguration.class,
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
public class Application implements CommandLineRunner {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private TraderController traderController;

  @Value("AAPL")
  private String[] initDailyList;

  @Autowired
  private QuoteService quoteService;

  @Override
  public void run(String... args) throws Exception {

    quoteService.initQuotes(Arrays.asList(initDailyList));
    quoteService.updateMarketData();
  }

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);

    //Turn off web
    //app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
  }

}
