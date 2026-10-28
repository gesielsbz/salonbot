package br.com.salonbot.api;
import br.com.salonbot.service.ProfessionalManager;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/professionals")
public class ProfessionalController {
  private final ProfessionalManager manager;
  public ProfessionalController(ProfessionalManager manager){this.manager=manager;}
  @GetMapping public List<ApiDtos.ProfessionalResponse> list(){return manager.list();}
  @PostMapping public ApiDtos.ProfessionalResponse create(@Valid @RequestBody ApiDtos.CreateProfessionalRequest r){return manager.create(r);}
}
