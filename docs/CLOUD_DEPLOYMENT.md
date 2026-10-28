# Implantação em nuvem — checklist

Topologia recomendada: frontend estático em CDN/hosting HTTPS; backend em container gerenciado; PostgreSQL gerenciado em rede privada; n8n separado com persistência e chave de criptografia; secret manager, métricas, logs e alertas.

Defina secrets no provedor: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, credenciais Meta, Google OAuth, IA e pagamento, além do segredo entre backend e n8n. Não versione `.env`.

Antes de publicar:
1. Implementar OIDC/JWT e autorização por função; derivar tenant da identidade autenticada.
2. Desativar o tenant por header de desenvolvimento e não expor rotas de negócio sem segurança.
3. Configurar TLS, DNS, CORS restrito e webhook HTTPS.
4. Executar build/testes e scan de dependências em CI.
5. Aplicar Flyway em etapa controlada, com backup e rollback.
6. Ativar backup, restauração testada, métricas, alertas e rate limiting.
7. Testar isolamento entre tenants, concorrência e webhooks duplicados.

Docker Compose é para desenvolvimento; não exponha PostgreSQL publicamente nem reutilize senhas locais.
