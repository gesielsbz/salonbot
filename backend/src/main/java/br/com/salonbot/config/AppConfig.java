package br.com.salonbot.config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@EnableConfigurationProperties(TenantProperties.class)
public class AppConfig {}
