package br.com.salonbot.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
@Entity @Table(name="service")
public class SalonService {
  @Id private UUID id;
  @Column(name="tenant_id",nullable=false) private UUID tenantId;
  @Column(nullable=false,length=160) private String name;
  @Column(columnDefinition="text") private String description;
  @Column(name="duration_minutes",nullable=false) private int durationMinutes;
  @Column(nullable=false,precision=12,scale=2) private BigDecimal price;
  @Column(nullable=false) private boolean active=true;
  @Column(name="created_at",nullable=false) private OffsetDateTime createdAt;
  protected SalonService(){}
  public SalonService(UUID tenantId,String name,int durationMinutes,BigDecimal price,String description){
    this.id=UUID.randomUUID();this.tenantId=tenantId;this.name=name;this.durationMinutes=durationMinutes;
    this.price=price;this.description=description;this.createdAt=OffsetDateTime.now();
  }
  public UUID getId(){return id;} public UUID getTenantId(){return tenantId;} public String getName(){return name;}
  public int getDurationMinutes(){return durationMinutes;} public BigDecimal getPrice(){return price;} public boolean isActive(){return active;}
}
