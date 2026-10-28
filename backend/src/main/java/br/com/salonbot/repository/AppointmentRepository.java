package br.com.salonbot.repository;
import br.com.salonbot.domain.Appointment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.OffsetDateTime;
import java.util.*;
public interface AppointmentRepository extends JpaRepository<Appointment,UUID> {
  @Query("""
    select count(a) > 0 from Appointment a
    where a.tenantId=:tenantId and a.professionalId=:professionalId
      and a.status <> 'CANCELLED' and a.startAt < :endAt and a.endAt > :startAt
    """)
  boolean existsOverlap(@Param("tenantId") UUID tenantId,@Param("professionalId") UUID professionalId,
                        @Param("startAt") OffsetDateTime startAt,@Param("endAt") OffsetDateTime endAt);
  @Query("select a from Appointment a where a.tenantId=:tenantId and a.startAt < :to and a.endAt > :from order by a.startAt")
  List<Appointment> findInRange(@Param("tenantId") UUID tenantId,@Param("from") OffsetDateTime from,@Param("to") OffsetDateTime to);
  Optional<Appointment> findByIdAndTenantId(UUID id,UUID tenantId);
}
