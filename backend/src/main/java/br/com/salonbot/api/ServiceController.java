package br.com.salonbot.api;
import br.com.salonbot.service.SalonServiceManager;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/services")
public class ServiceController {
  private final SalonServiceManager manager;
  public ServiceController(SalonServiceManager manager){this.manager=manager;}
  @GetMapping public List<ApiDtos.ServiceResponse> list(){return manager.list();}
  @PostMapping public ApiDtos.ServiceResponse create(@Valid @RequestBody ApiDtos.CreateServiceRequest r){return manager.create(r);}
}
