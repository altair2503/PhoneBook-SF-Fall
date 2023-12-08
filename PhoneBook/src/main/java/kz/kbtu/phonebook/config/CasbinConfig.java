package kz.kbtu.phonebook.config;

import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CasbinConfig {
    @Value("${casbin.model-path}")
    private String modelPath;
    @Value("${casbin.model-path}")
    private String policyFilePath;
    @Bean
    public Enforcer enforcer() {
        return new Enforcer(modelPath, policyFilePath);
    }
}
