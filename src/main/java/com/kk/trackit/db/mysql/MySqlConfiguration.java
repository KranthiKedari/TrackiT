package com.kk.trackit.db.mysql;

/**
 * Created by kkedari on 7/12/15.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@PropertySource("classpath:trackit.properties")
public class MySqlConfiguration
{
    @Value("${jdbc.driverClassName:com.mysql.jdbc.Driver}")
    private String jdbcDriverClassName;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUserName;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource bean = new DriverManagerDataSource();
        bean.setDriverClassName(jdbcDriverClassName);
        bean.setUrl(jdbcUrl);
        bean.setUsername(jdbcUserName);
        bean.setPassword(jdbcPassword);
        return bean;
    }


    @Autowired
    @Bean
    public DataSourceTransactionManager transactionManager(DriverManagerDataSource dataSource)
    {
        DataSourceTransactionManager result = new DataSourceTransactionManager();
        result.setDataSource(dataSource);

        return result;
    }

    @Autowired
    @Bean
    public TransactionTemplate transactionTemplate(DataSourceTransactionManager dataSourceTransactionManager)
    {
        return new TransactionTemplate(dataSourceTransactionManager);
    }

    @Autowired
    @Bean
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }
}
