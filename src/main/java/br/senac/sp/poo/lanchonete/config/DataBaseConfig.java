package br.senac.sp.poo.lanchonete.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
        (DataBaseProperties.class)
public class DataBaseConfig {
    private final DataBaseProperties PROPS;

    public DataBaseConfig(DataBaseProperties props) {
        this.PROPS = props;
    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url(PROPS.getUrl());
        builder.username(PROPS.getUsername());
        builder.password(PROPS.getPassword());
        builder.driverClassName(PROPS.getDriverClassName());
        return builder.build();
    }
}
