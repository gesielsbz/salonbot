package br.com.salonbot.repository;
import br.com.salonbot.domain.Professional;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
import java.util.UUID;
public interface ProfessionalRepository extends JpaRepository<Professional,UUID> {
  List<Professional> findByTenantIdOrderByName(UUID tenantId);
  Optional<Professional> findByIdAndTenantId(UUID id,UUID tenantId);
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select p from Professional p where p.id=:id and p.tenantId=:tenantId and p.active=true")
  Optional<Professional> lockActiveByIdAndTenantId(@Param("id") UUID id,@Param("tenantId") UUID tenantId);
}
