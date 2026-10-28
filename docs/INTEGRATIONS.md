# Contratos de integração

## WhatsApp Cloud API — configuração manual necessária
No Meta for Developers, crie App, configure WhatsApp, associe WABA/número, configure token de produção, permissões e webhook HTTPS. Configure `META_PHONE_NUMBER_ID`, `META_WABA_ID`, `META_ACCESS_TOKEN`, `META_WEBHOOK_VERIFY_TOKEN`, `META_APP_SECRET` e uma versão Graph API válida no ambiente. Nenhuma credencial real é incluída.

Endpoints planejados: `GET/POST /api/v1/webhooks/whatsapp`. Ainda não implementados. O GET deve validar o challenge; o POST deve validar `X-Hub-Signature-256` usando o corpo bruto, deduplicar eventos na `webhook_inbox` e responder rapidamente antes do processamento assíncrono. O tenant deve ser resolvido pelo `phone_number_id` cadastrado, não confiado a partir do payload.

Documentação: https://developers.facebook.com/docs/whatsapp/cloud-api/

## n8n
Contrato de evento interno sugerido:
```json
{
  "eventId": "uuid",
  "tenantId": "uuid",
  "type": "APPOINTMENT_CONFIRMED",
  "aggregateId": "appointment-uuid",
  "occurredAt": "2026-10-20T16:00:00Z",
  "payload": {"customerPhone":"+5534999999999","serviceName":"Corte","startAt":"2026-10-21T13:30:00Z"}
}
```
Workflows propostos: `WF-01-whatsapp-router`, `WF-02-appointment-notifications`, `WF-03-google-calendar-sync`, `WF-04-error-handler`. Use autenticação serviço-a-serviço, timeout, retries limitados e logs sem segredos. Ainda não há workflow importável nesta entrega.

## Google Calendar
No Google Cloud Console, habilite Calendar API, configure OAuth consent screen, OAuth client e redirect URI HTTPS. Solicite escopos mínimos. Tokens e refresh tokens devem ser criptografados em repouso e nunca enviados ao browser/n8n. Implemente state anti-CSRF, troca do authorization code, criação/atualização/exclusão de eventos e reconciliação periódica. A interface `CalendarGateway` em `IntegrationContracts.java` define o contrato, não o adaptador real.

Documentação: https://developers.google.com/workspace/calendar/api/guides/create-events

## Pagamentos
Escolha o provedor antes de implementar. Contrato `BillingGateway` define checkout; será necessário endpoint de checkout e webhook com assinatura validada, idempotência e estado de assinatura. Nunca confie em status de pagamento enviado pelo browser nem armazene dados completos de cartão.

## IA
A IA deve retornar intenção estruturada, nunca confirmar uma reserva:
```json
{"intent":"BOOK_APPOINTMENT","serviceName":"corte","professionalName":"Ana","dateExpression":"sexta-feira","period":"AFTERNOON","missingFields":[],"needsClarification":false}
```
O backend valida nomes, data, tenant e disponibilidade. Só a resposta transacional da API confirma o agendamento.
