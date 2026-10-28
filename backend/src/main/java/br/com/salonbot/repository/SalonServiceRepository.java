package br.com.salonbot.repository;
import br.com.salonbot.domain.SalonService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface SalonServiceRepository extends JpaRepository<SalonService,UUID> {
  List<SalonService> findByTenantIdAndActiveTrueOrderByName(UUID tenantId);
  Optional<SalonService> findByIdAndTenantIdAndActiveTrue(UUID id,UUID tenantId);
}
