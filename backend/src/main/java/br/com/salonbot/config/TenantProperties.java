package br.com.salonbot.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.UUID;
@ConfigurationProperties(prefix = "salonbot.tenant")
public record TenantProperties(UUID devTenantId) {}
