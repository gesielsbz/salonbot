package br.com.salonbot.domain;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
@Entity @Table(name="customer")
public class Customer {
  @Id private UUID id;
  @Column(name="tenant_id",nullable=false) private UUID tenantId;
  @Column(nullable=false,length=160) private String name;
  @Column(nullable=false,length=32) private String phone;
  @Column(name="consent_status",nullable=false,length=20) private String consentStatus="UNKNOWN";
  @Column(name="created_at",nullable=false) private OffsetDateTime createdAt;
  protected Customer(){}
  public Customer(UUID tenantId,String name,String phone){this.id=UUID.randomUUID();this.tenantId=tenantId;this.name=name;this.phone=phone;this.createdAt=OffsetDateTime.now();}
  public UUID getId(){return id;} public UUID getTenantId(){return tenantId;} public String getName(){return name;} public String getPhone(){return phone;}
}
