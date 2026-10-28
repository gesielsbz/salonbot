# Contratos REST v1

Em desenvolvimento, envie `X-Tenant-Id: 11111111-1111-1111-1111-111111111111`.

## Services
`GET /api/v1/services`
`POST /api/v1/services`
```json
{"name":"Corte","durationMinutes":45,"price":60.00,"description":"Corte tradicional"}
```

## Professionals
`GET /api/v1/professionals`
`POST /api/v1/professionals`
```json
{"name":"Ana Souza","email":"ana@example.com"}
```

## Customers
`POST /api/v1/customers`
```json
{"name":"Maria","phone":"+5534999999999"}
```

## Appointments
`POST /api/v1/appointments`
```json
{"customerId":"uuid","professionalId":"uuid","serviceId":"uuid","startAt":"2026-10-21T13:30:00Z"}
```
`GET /api/v1/appointments?from=2026-10-20T00:00:00Z&to=2026-11-01T00:00:00Z`
`POST /api/v1/appointments/{id}/cancel`

## Availability
`GET /api/v1/availability?professionalId=uuid&serviceId=uuid&from=2026-10-21T00:00:00Z&to=2026-10-22T00:00:00Z`

Nesta versão a rota devolve reservas ocupadas, não slots livres. Expediente, folgas, bloqueios e slot generation devem ser adicionados antes de habilitar reservas autônomas pelo chatbot.

## Erros
`{"code":"REQUEST_ERROR","message":"Time slot is no longer available"}`. Conflitos retornam HTTP 409.
