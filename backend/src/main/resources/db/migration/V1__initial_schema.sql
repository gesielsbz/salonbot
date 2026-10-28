CREATE TABLE tenant (
  id UUID PRIMARY KEY,
  name VARCHAR(160) NOT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  timezone VARCHAR(80) NOT NULL DEFAULT 'America/Sao_Paulo',
  status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT ck_tenant_status CHECK (status IN ('ACTIVE','SUSPENDED','CLOSED'))
);
CREATE TABLE professional (
  id UUID PRIMARY KEY, tenant_id UUID NOT NULL REFERENCES tenant(id),
  name VARCHAR(160) NOT NULL, email VARCHAR(255), active BOOLEAN NOT NULL DEFAULT TRUE,
  calendar_id VARCHAR(255), created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT uq_professional_tenant_id UNIQUE (tenant_id,id)
);
CREATE INDEX ix_professional_tenant ON professional(tenant_id,active);
CREATE TABLE service (
  id UUID PRIMARY KEY, tenant_id UUID NOT NULL REFERENCES tenant(id),
  name VARCHAR(160) NOT NULL, description TEXT, duration_minutes INTEGER NOT NULL,
  price NUMERIC(12,2) NOT NULL, active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT ck_service_duration CHECK (duration_minutes BETWEEN 5 AND 600),
  CONSTRAINT ck_service_price CHECK (price >= 0),
  CONSTRAINT uq_service_tenant_id UNIQUE (tenant_id,id)
);
CREATE INDEX ix_service_tenant ON service(tenant_id,active);
CREATE TABLE customer (
  id UUID PRIMARY KEY, tenant_id UUID NOT NULL REFERENCES tenant(id),
  name VARCHAR(160) NOT NULL, phone VARCHAR(32) NOT NULL,
  consent_status VARCHAR(20) NOT NULL DEFAULT 'UNKNOWN',
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT ck_customer_consent CHECK (consent_status IN ('UNKNOWN','OPTED_IN','OPTED_OUT')),
  CONSTRAINT uq_customer_tenant_id UNIQUE (tenant_id,id)
);
CREATE INDEX ix_customer_tenant_phone ON customer(tenant_id,phone);
CREATE TABLE appointment (
  id UUID PRIMARY KEY, tenant_id UUID NOT NULL REFERENCES tenant(id),
  customer_id UUID NOT NULL, professional_id UUID NOT NULL, service_id UUID NOT NULL,
  start_at TIMESTAMPTZ NOT NULL, end_at TIMESTAMPTZ NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(), updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT ck_appointment_interval CHECK (end_at > start_at),
  CONSTRAINT ck_appointment_status CHECK (status IN ('PENDING','CONFIRMED','CANCELLED','COMPLETED','NO_SHOW')),
  CONSTRAINT fk_appointment_customer FOREIGN KEY (tenant_id,customer_id) REFERENCES customer(tenant_id,id),
  CONSTRAINT fk_appointment_professional FOREIGN KEY (tenant_id,professional_id) REFERENCES professional(tenant_id,id),
  CONSTRAINT fk_appointment_service FOREIGN KEY (tenant_id,service_id) REFERENCES service(tenant_id,id)
);
CREATE INDEX ix_appointment_tenant_prof_start ON appointment(tenant_id,professional_id,start_at);
CREATE INDEX ix_appointment_tenant_start ON appointment(tenant_id,start_at);
CREATE TABLE calendar_sync (
  id UUID PRIMARY KEY, tenant_id UUID NOT NULL REFERENCES tenant(id), appointment_id UUID NOT NULL REFERENCES appointment(id),
  provider VARCHAR(40) NOT NULL, calendar_id VARCHAR(255), external_event_id VARCHAR(255),
  sync_status VARCHAR(20) NOT NULL DEFAULT 'PENDING', last_error TEXT, updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT ck_calendar_sync_status CHECK (sync_status IN ('PENDING','SYNCED','FAILED','DELETED'))
);
CREATE TABLE webhook_inbox (
  id UUID PRIMARY KEY, provider VARCHAR(40) NOT NULL, external_event_id VARCHAR(255) NOT NULL,
  received_at TIMESTAMPTZ NOT NULL DEFAULT now(), processed_at TIMESTAMPTZ,
  status VARCHAR(20) NOT NULL DEFAULT 'RECEIVED', payload JSONB,
  CONSTRAINT uq_webhook_provider_event UNIQUE(provider,external_event_id)
);
CREATE TABLE conversation (
  id UUID PRIMARY KEY, tenant_id UUID NOT NULL REFERENCES tenant(id), customer_id UUID REFERENCES customer(id),
  phone VARCHAR(32) NOT NULL, state VARCHAR(40) NOT NULL DEFAULT 'MAIN_MENU',
  context_json JSONB NOT NULL DEFAULT '{}'::jsonb, expires_at TIMESTAMPTZ, updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX ix_conversation_tenant_phone ON conversation(tenant_id,phone);
CREATE TABLE subscription (
  id UUID PRIMARY KEY, tenant_id UUID NOT NULL REFERENCES tenant(id), plan_code VARCHAR(40) NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'TRIAL', provider VARCHAR(40), provider_subscription_id VARCHAR(255),
  period_end TIMESTAMPTZ, created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT ck_subscription_status CHECK (status IN ('TRIAL','ACTIVE','PAST_DUE','CANCELLED','SUSPENDED'))
);
CREATE INDEX ix_subscription_tenant_status ON subscription(tenant_id,status);
