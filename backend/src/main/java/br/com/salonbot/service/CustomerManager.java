package br.com.salonbot.service;
import br.com.salonbot.api.ApiDtos;
import br.com.salonbot.domain.Customer;
import br.com.salonbot.repository.CustomerRepository;
import br.com.salonbot.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CustomerManager {
  private final CustomerRepository repository;
  public CustomerManager(CustomerRepository repository){this.repository=repository;}
  @Transactional public ApiDtos.CustomerResponse create(ApiDtos.CreateCustomerRequest r){
    var c=repository.save(new Customer(TenantContext.requireTenantId(),r.name(),r.phone()));
    return new ApiDtos.CustomerResponse(c.getId(),c.getName(),c.getPhone());
  }
}
