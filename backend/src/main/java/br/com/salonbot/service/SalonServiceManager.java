package br.com.salonbot.service;
import br.com.salonbot.api.ApiDtos;
import br.com.salonbot.domain.SalonService;
import br.com.salonbot.repository.SalonServiceRepository;
import br.com.salonbot.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class SalonServiceManager {
  private final SalonServiceRepository repository;
  public SalonServiceManager(SalonServiceRepository repository){this.repository=repository;}
  @Transactional(readOnly=true) public List<ApiDtos.ServiceResponse> list(){
    return repository.findByTenantIdAndActiveTrueOrderByName(TenantContext.requireTenantId()).stream().map(this::response).toList();
  }
  @Transactional public ApiDtos.ServiceResponse create(ApiDtos.CreateServiceRequest r){
    return response(repository.save(new SalonService(TenantContext.requireTenantId(),r.name(),r.durationMinutes(),r.price(),r.description())));
  }
  private ApiDtos.ServiceResponse response(SalonService s){return new ApiDtos.ServiceResponse(s.getId(),s.getName(),s.getDurationMinutes(),s.getPrice(),s.isActive());}
}
