# Pendências obrigatórias antes de produção

1. Autenticação OIDC/JWT, funções OWNER/MANAGER/PROFESSIONAL e tenant derivado da identidade.
2. Remover header `X-Tenant-Id` como mecanismo de identidade em produção.
3. Expediente, feriados, folgas, bloqueios, intervalos e geração real de slots.
4. Outbox transacional e worker idempotente para sincronizações.
5. Webhooks Meta com validação HMAC, status callbacks, templates, opt-out e rate limiting.
6. Google OAuth state/CSRF, criptografia de tokens, refresh, push channels e reconciliação.
7. Escolher provedor de pagamento, checkout, webhook assinado e idempotência.
8. IA com schema de saída, limites, proteção contra prompt injection e handoff humano.
9. LGPD: finalidade, base legal, retenção, exclusão, direitos dos titulares e contratos com operadores.
10. Auditoria, logs estruturados, métricas, alertas, backups, restore drills e gestão de vulnerabilidades.
11. Testes de concorrência com transações separadas e testes de isolamento multi-tenant.
