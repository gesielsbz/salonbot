package br.com.salonbot.domain;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
@Entity @Table(name="appointment")
public class Appointment {
  @Id private UUID id;
  @Column(name="tenant_id",nullable=false) private UUID tenantId;
  @Column(name="customer_id",nullable=false) private UUID customerId;
  @Column(name="professional_id",nullable=false) private UUID professionalId;
  @Column(name="service_id",nullable=false) private UUID serviceId;
  @Column(name="start_at",nullable=false) private OffsetDateTime startAt;
  @Column(name="end_at",nullable=false) private OffsetDateTime endAt;
  @Column(nullable=false,length=20) private String status;
  @Column(name="created_at",nullable=false) private OffsetDateTime createdAt;
  @Column(name="updated_at",nullable=false) private OffsetDateTime updatedAt;
  protected Appointment(){}
  public Appointment(UUID tenantId,UUID customerId,UUID professionalId,UUID serviceId,OffsetDateTime startAt,OffsetDateTime endAt){
    this.id=UUID.randomUUID();this.tenantId=tenantId;this.customerId=customerId;this.professionalId=professionalId;
    this.serviceId=serviceId;this.startAt=startAt;this.endAt=endAt;this.status="CONFIRMED";
    this.createdAt=OffsetDateTime.now();this.updatedAt=this.createdAt;
  }
  public UUID getId(){return id;} public UUID getTenantId(){return tenantId;} public UUID getCustomerId(){return customerId;}
  public UUID getProfessionalId(){return professionalId;} public UUID getServiceId(){return serviceId;}
  public OffsetDateTime getStartAt(){return startAt;} public OffsetDateTime getEndAt(){return endAt;} public String getStatus(){return status;}
  public void cancel(){this.status="CANCELLED";this.updatedAt=OffsetDateTime.now();}
}
