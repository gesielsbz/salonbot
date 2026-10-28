package br.com.salonbot.domain;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
@Entity @Table(name="professional")
public class Professional {
  @Id private UUID id;
  @Column(name="tenant_id",nullable=false) private UUID tenantId;
  @Column(nullable=false,length=160) private String name;
  @Column(length=255) private String email;
  @Column(nullable=false) private boolean active=true;
  @Column(name="calendar_id",length=255) private String calendarId;
  @Column(name="created_at",nullable=false) private OffsetDateTime createdAt;
  protected Professional(){}
  public Professional(UUID tenantId,String name,String email){this.id=UUID.randomUUID();this.tenantId=tenantId;this.name=name;this.email=email;this.createdAt=OffsetDateTime.now();}
  public UUID getId(){return id;} public UUID getTenantId(){return tenantId;} public String getName(){return name;}
  public String getEmail(){return email;} public boolean isActive(){return active;}
}
