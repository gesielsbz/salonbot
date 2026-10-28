package br.com.salonbot.tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface TenantRepository extends JpaRepository<TenantEntity,UUID> {}
