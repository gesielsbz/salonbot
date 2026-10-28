package br.com.salonbot.api;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/v1/integrations")
public class IntegrationController {
  @GetMapping public Map<String,Object> status(){return Map.of(
    "whatsapp",Map.of("status","NOT_CONFIGURED","setup","Configure Meta app, WABA, phone number and webhook."),
    "googleCalendar",Map.of("status","NOT_CONFIGURED","setup","Configure Google OAuth client and calendar permissions."),
    "n8n",Map.of("status","CONTRACT_ONLY","setup","Import workflows and configure authenticated callbacks."),
    "payments",Map.of("status","NOT_CONFIGURED","setup","Choose provider and implement signed webhooks."));}
}
