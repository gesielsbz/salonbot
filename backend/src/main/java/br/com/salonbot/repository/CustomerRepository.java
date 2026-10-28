package br.com.salonbot.repository;
import br.com.salonbot.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface CustomerRepository extends JpaRepository<Customer,UUID> {
  Optional<Customer> findByIdAndTenantId(UUID id,UUID tenantId);
}
