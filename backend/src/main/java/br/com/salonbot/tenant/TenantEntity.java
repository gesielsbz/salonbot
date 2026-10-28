package br.com.salonbot.tenant;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
@Entity @Table(name="tenant")
public class TenantEntity {
  @Id private UUID id;
  @Column(nullable=false,length=160) private String name;
  @Column(nullable=false,unique=true,length=100) private String slug;
  @Column(nullable=false,length=80) private String timezone="America/Sao_Paulo";
  @Column(nullable=false,length=20) private String status="ACTIVE";
  @Column(name="created_at",nullable=false) private OffsetDateTime createdAt;
  protected TenantEntity() {}
  public TenantEntity(UUID id,String name,String slug) { this.id=id; this.name=name; this.slug=slug; this.createdAt=OffsetDateTime.now(); }
  @PrePersist void prePersist(){ if(createdAt==null) createdAt=OffsetDateTime.now(); }
  public UUID getId(){return id;}
}
