package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.util.StringUtil;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public HttpClientConnectionManager httpClientConnectionManager() {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);
    return cm;
  }


  @Bean
  public DataSource dataSource() {
    String JDBC_URL = System.getenv("PSQL_URL");
    String DB_USER = System.getenv("PSQL_USER");
    String DB_PASSWORD = System.getenv("PSQL_PASSWORD");

    if (StringUtil.isEmpty(JDBC_URL, DB_USER, DB_PASSWORD)) {
      throw new IllegalArgumentException("Missing data source config env vars");
    }

    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setUrl(JDBC_URL);
    basicDataSource.setUsername(DB_USER);
    basicDataSource.setPassword(DB_PASSWORD);
    return basicDataSource;
  }
}
