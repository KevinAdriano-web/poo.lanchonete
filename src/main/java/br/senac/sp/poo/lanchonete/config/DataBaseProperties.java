package br.senac.sp.poo.lanchonete.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Getter
@Setter
@ConfigurationProperties(prefix = "app.datasource")
public class DataBaseProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
