package br.com.salonbot.integration;
import java.time.OffsetDateTime;
import java.util.UUID;
/** Internal interfaces; provider adapters are deliberately not activated until credentials are configured. */
public final class IntegrationContracts {
  private IntegrationContracts(){}
  public record OutboundMessage(String tenantId,String toE164,String text,String templateName){}
  public record MessageReceipt(String providerMessageId,String status){}
  public interface WhatsAppGateway { MessageReceipt send(OutboundMessage message); }
  public record CalendarEvent(String calendarId,String eventId,String title,OffsetDateTime startAt,OffsetDateTime endAt,String status){}
  public interface CalendarGateway {
    CalendarEvent upsert(UUID tenantId,UUID appointmentId,CalendarEvent event);
    void delete(UUID tenantId,String calendarId,String eventId);
  }
  public record AutomationEvent(String eventId,String tenantId,String type,String aggregateId,String occurredAt,String payloadJson){}
  public interface AutomationPublisher { void publish(AutomationEvent event); }
  public record CheckoutRequest(UUID tenantId,String planCode,String successUrl,String cancelUrl){}
  public record CheckoutSession(String providerSessionId,String checkoutUrl){}
  public interface BillingGateway { CheckoutSession createCheckout(CheckoutRequest request); }
}
