package ca.jrvs.apps.trading;


import ca.jrvs.apps.trading.controller.TraderController;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
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
  private TraderController traderController;

  @Value("AEO")
  private String[] initDailyList;

  @Override
  public void run(String... args) throws Exception {
    String first_name = "Steve";
    String last_name = "Ma";
    Date dob = Date.valueOf("2015-03-31");
    String country = "China";
    String email = "jrvs@gmail.com";
    Trader trader = new Trader(first_name, last_name, dob, country, email);

    traderController.createTrader(trader);

  }

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);

    //Turn off web
    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
  }

}
