package br.com.salonbot.api;
import br.com.salonbot.service.AppointmentManager;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.*;
@RestController @RequestMapping("/api/v1")
public class AppointmentController {
  private final AppointmentManager manager;
  public AppointmentController(AppointmentManager manager){this.manager=manager;}
  @PostMapping("/appointments") public ApiDtos.AppointmentResponse create(@Valid @RequestBody ApiDtos.CreateAppointmentRequest r){return manager.create(r);}
  @GetMapping("/appointments") public List<ApiDtos.AppointmentResponse> list(
    @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
    @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to){return manager.list(from,to);}
  @PostMapping("/appointments/{id}/cancel") public ApiDtos.AppointmentResponse cancel(@PathVariable UUID id){return manager.cancel(id);}
  @GetMapping("/availability") public List<ApiDtos.AppointmentResponse> availability(@RequestParam UUID professionalId,@RequestParam UUID serviceId,
    @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
    @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to){return manager.availability(professionalId,serviceId,from,to);}
}
