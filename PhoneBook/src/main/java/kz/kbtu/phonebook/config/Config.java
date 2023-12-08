package kz.kbtu.phonebook.config;

import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Model;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Enforcer enforcer() {
        return new Enforcer(
                "/Users/nursultanturugeldiev/Desktop/PhoneBook-SF-Fall/PhoneBook/src/main/resources/model.conf",
                "/Users/nursultanturugeldiev/Desktop/PhoneBook-SF-Fall/PhoneBook/src/main/resources/policy.csv");
    }
}
