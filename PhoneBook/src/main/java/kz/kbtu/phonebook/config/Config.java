package kz.kbtu.phonebook.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Model;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class Config {
    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Value("${spring.datasource.hikari.cachePrepStmts}")
    private boolean cachePrepStmts;

    @Value("${spring.datasource.hikari.prepStmtCacheSize}")
    private int prepStmtCacheSize;

    @Value("${spring.datasource.hikari.prepStmtCacheSqlLimit}")
    private int prepStmtCacheSqlLimit;
    @Value("${spring.flyway.locations}")
    private String locations;
    @Value("${spring.flyway.schemasName}")
    private String schemasName;
    @Bean
    public HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(databaseUrl);
        config.setUsername(databaseUsername);
        config.setPassword(databasePassword);
        config.addDataSourceProperty("cachePrepStmts", String.valueOf(cachePrepStmts));
        config.addDataSourceProperty("prepStmtCacheSize", String.valueOf(prepStmtCacheSize));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", String.valueOf(prepStmtCacheSqlLimit));
        return new HikariDataSource(config);
    }
    @Bean
    public Connection getConnection() throws SQLException {
        return dataSource().getConnection();
    }
    @Bean
    public Flyway flywayMigrate(){
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource())
                .schemas(schemasName)
                .locations(locations)
                .load();
        flyway.migrate();
        return flyway;
    }
}
