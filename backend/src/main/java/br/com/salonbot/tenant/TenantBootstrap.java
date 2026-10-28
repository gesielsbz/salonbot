package br.com.salonbot.tenant;
import br.com.salonbot.config.TenantProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component @Profile("!production")
public class TenantBootstrap implements CommandLineRunner {
  private final TenantRepository repository; private final TenantProperties properties;
  public TenantBootstrap(TenantRepository repository,TenantProperties properties){this.repository=repository;this.properties=properties;}
  @Override @Transactional public void run(String... args){
    var id=properties.devTenantId();
    if(!repository.existsById(id)) repository.save(new TenantEntity(id,"Salão Demonstração","salao-demonstracao"));
  }
}
