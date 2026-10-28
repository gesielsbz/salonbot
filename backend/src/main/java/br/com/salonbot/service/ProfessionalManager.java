package br.com.salonbot.service;
import br.com.salonbot.api.ApiDtos;
import br.com.salonbot.domain.Professional;
import br.com.salonbot.repository.ProfessionalRepository;
import br.com.salonbot.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class ProfessionalManager {
  private final ProfessionalRepository repository;
  public ProfessionalManager(ProfessionalRepository repository){this.repository=repository;}
  @Transactional(readOnly=true) public List<ApiDtos.ProfessionalResponse> list(){
    return repository.findByTenantIdOrderByName(TenantContext.requireTenantId()).stream().map(this::response).toList();
  }
  @Transactional public ApiDtos.ProfessionalResponse create(ApiDtos.CreateProfessionalRequest r){
    return response(repository.save(new Professional(TenantContext.requireTenantId(),r.name(),r.email())));
  }
  private ApiDtos.ProfessionalResponse response(Professional p){return new ApiDtos.ProfessionalResponse(p.getId(),p.getName(),p.getEmail(),p.isActive());}
}
