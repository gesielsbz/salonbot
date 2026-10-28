package br.com.salonbot.api;
import br.com.salonbot.service.CustomerManager;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/customers")
public class CustomerController {
  private final CustomerManager manager;
  public CustomerController(CustomerManager manager){this.manager=manager;}
  @PostMapping public ApiDtos.CustomerResponse create(@Valid @RequestBody ApiDtos.CreateCustomerRequest r){return manager.create(r);}
}
