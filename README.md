# SalonBot — SaaS de agendamento

Base inicial com Java 17, Spring Boot, PostgreSQL/Flyway, React/TypeScript e Docker Compose.

## Executar
1. Copie `.env.example` para `.env`.
2. Execute `docker compose up --build`.
3. Abra http://localhost:5173 e a API em http://localhost:8080/actuator/health.

O tenant de demonstração é `11111111-1111-1111-1111-111111111111`. No ambiente local, as rotas de negócio aceitam `X-Tenant-Id`. **Esse mecanismo é apenas para desenvolvimento; não publique em produção sem autenticação OIDC/JWT e tenant derivado da identidade autenticada.**

## API
- `GET/POST /api/v1/services`
- `GET/POST /api/v1/professionals`
- `POST /api/v1/customers`
- `GET /api/v1/availability?professionalId=UUID&serviceId=UUID&from=ISO&to=ISO`
- `POST /api/v1/appointments`
- `GET /api/v1/appointments?from=ISO&to=ISO`
- `POST /api/v1/appointments/{id}/cancel`
- `GET /api/v1/integrations`

Consulte `docs/` para contratos, configuração externa e lacunas obrigatórias antes de produção.

## Testes
`cd backend && mvn test` (Testcontainers exige Docker).

## Limitações honestas desta entrega
- A agenda bloqueia conflitos concorrentes para o mesmo profissional usando lock pessimista.
- A rota de disponibilidade devolve os intervalos ocupados; geração de slots livres requer expediente, folgas e bloqueios, ainda não implementados.
- WhatsApp, n8n, Google Calendar e pagamentos estão definidos por contratos/documentação, mas não conectados a contas reais.
- Frontend é um painel inicial técnico, não um produto final.
- O perfil `production` não tem autenticação implementada: não publique rotas de negócio antes de completar `docs/PRODUCTION_GAPS.md`.
