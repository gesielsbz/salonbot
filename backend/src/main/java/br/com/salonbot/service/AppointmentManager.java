package br.com.salonbot.service;
import br.com.salonbot.api.ApiDtos;
import br.com.salonbot.domain.Appointment;
import br.com.salonbot.repository.*;
import br.com.salonbot.tenant.TenantContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.OffsetDateTime;
import java.util.*;
@Service
public class AppointmentManager {
  private final AppointmentRepository appointments; private final ProfessionalRepository professionals;
  private final SalonServiceRepository services; private final CustomerRepository customers;
  public AppointmentManager(AppointmentRepository a,ProfessionalRepository p,SalonServiceRepository s,CustomerRepository c){
    appointments=a;professionals=p;services=s;customers=c;
  }
  @Transactional public ApiDtos.AppointmentResponse create(ApiDtos.CreateAppointmentRequest r){
    UUID tenant=TenantContext.requireTenantId();
    professionals.lockActiveByIdAndTenantId(r.professionalId(),tenant)
      .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Professional not found"));
    var customer=customers.findByIdAndTenantId(r.customerId(),tenant)
      .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer not found"));
    var service=services.findByIdAndTenantIdAndActiveTrue(r.serviceId(),tenant)
      .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Service not found"));
    OffsetDateTime start=r.startAt();
    if(!start.isAfter(OffsetDateTime.now())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Appointment must start in the future");
    OffsetDateTime end=start.plusMinutes(service.getDurationMinutes());
    if(appointments.existsOverlap(tenant,r.professionalId(),start,end))
      throw new ResponseStatusException(HttpStatus.CONFLICT,"Time slot is no longer available");
    return response(appointments.save(new Appointment(tenant,customer.getId(),r.professionalId(),service.getId(),start,end)));
  }
  @Transactional(readOnly=true) public List<ApiDtos.AppointmentResponse> list(OffsetDateTime from,OffsetDateTime to){
    if(from==null||to==null||!to.isAfter(from)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Valid from and to are required");
    return appointments.findInRange(TenantContext.requireTenantId(),from,to).stream().map(this::response).toList();
  }
  @Transactional public ApiDtos.AppointmentResponse cancel(UUID id){
    var a=appointments.findByIdAndTenantId(id,TenantContext.requireTenantId())
      .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Appointment not found"));
    a.cancel(); return response(a);
  }
  @Transactional(readOnly=true) public List<ApiDtos.AppointmentResponse> availability(UUID professionalId,UUID serviceId,OffsetDateTime from,OffsetDateTime to){
    UUID tenant=TenantContext.requireTenantId();
    professionals.findByIdAndTenantId(professionalId,tenant).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Professional not found"));
    services.findByIdAndTenantIdAndActiveTrue(serviceId,tenant).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Service not found"));
    if(from==null||to==null||!to.isAfter(from)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Valid from and to are required");
    return appointments.findInRange(tenant,from,to).stream().filter(a->a.getProfessionalId().equals(professionalId)).map(this::response).toList();
  }
  private ApiDtos.AppointmentResponse response(Appointment a){return new ApiDtos.AppointmentResponse(a.getId(),a.getCustomerId(),a.getProfessionalId(),a.getServiceId(),a.getStartAt(),a.getEndAt(),a.getStatus());}
}
