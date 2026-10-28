package br.com.salonbot;
import br.com.salonbot.domain.*;
import br.com.salonbot.repository.*;
import br.com.salonbot.tenant.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
@Testcontainers @DataJpaTest
class AppointmentOverlapRepositoryTest {
  @Container static final PostgreSQLContainer<?> DB=new PostgreSQLContainer<>("postgres:16-alpine");
  @DynamicPropertySource static void db(DynamicPropertyRegistry r){
    r.add("spring.datasource.url",DB::getJdbcUrl);r.add("spring.datasource.username",DB::getUsername);r.add("spring.datasource.password",DB::getPassword);
  }
  @Autowired ProfessionalRepository professionals; @Autowired SalonServiceRepository services;
  @Autowired CustomerRepository customers; @Autowired AppointmentRepository appointments; @Autowired TenantRepository tenants;
  @Test void detectsOverlapButAllowsAdjacentSlot(){
    UUID t=UUID.randomUUID();tenants.save(new TenantEntity(t,"Teste","test-"+t));
    Professional p=professionals.save(new Professional(t,"Ana","ana@example.com"));
    SalonService s=services.save(new SalonService(t,"Corte",60,new BigDecimal("50.00"),null));
    Customer c=customers.save(new Customer(t,"Cliente","+5534999999999"));
    OffsetDateTime start=OffsetDateTime.now().plusDays(2).withMinute(0).withSecond(0).withNano(0);
    appointments.save(new Appointment(t,c.getId(),p.getId(),s.getId(),start,start.plusMinutes(60)));
    assertThat(appointments.existsOverlap(t,p.getId(),start.plusMinutes(30),start.plusMinutes(90))).isTrue();
    assertThat(appointments.existsOverlap(t,p.getId(),start.plusMinutes(60),start.plusMinutes(120))).isFalse();
  }
}
