package br.com.salonbot.api;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
public final class ApiDtos {
  private ApiDtos(){}
  public record CreateServiceRequest(@NotBlank @Size(max=160) String name,@NotNull @Min(5) @Max(600) Integer durationMinutes,
    @NotNull @DecimalMin("0.00") BigDecimal price,@Size(max=4000) String description){}
  public record ServiceResponse(UUID id,String name,int durationMinutes,BigDecimal price,boolean active){}
  public record CreateProfessionalRequest(@NotBlank @Size(max=160) String name,@Email @Size(max=255) String email){}
  public record ProfessionalResponse(UUID id,String name,String email,boolean active){}
  public record CreateCustomerRequest(@NotBlank @Size(max=160) String name,
    @NotBlank @Pattern(regexp="^\\\\+[1-9][0-9]{7,14}$",message="phone must be E.164, e.g. +5534999999999") String phone){}
  public record CustomerResponse(UUID id,String name,String phone){}
  public record CreateAppointmentRequest(@NotNull UUID customerId,@NotNull UUID professionalId,@NotNull UUID serviceId,@NotNull OffsetDateTime startAt){}
  public record AppointmentResponse(UUID id,UUID customerId,UUID professionalId,UUID serviceId,OffsetDateTime startAt,OffsetDateTime endAt,String status){}
  public record ErrorResponse(String code,String message){}
}
