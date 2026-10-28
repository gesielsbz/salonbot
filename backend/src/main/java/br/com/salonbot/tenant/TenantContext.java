package br.com.salonbot.tenant;
import java.util.UUID;
public final class TenantContext {
  private static final ThreadLocal<UUID> CURRENT = new ThreadLocal<>();
  private TenantContext() {}
  public static void set(UUID id) { CURRENT.set(id); }
  public static UUID get() { return CURRENT.get(); }
  public static UUID requireTenantId() {
    UUID id = CURRENT.get();
    if (id == null) throw new IllegalStateException("Tenant context is missing");
    return id;
  }
  public static void clear() { CURRENT.remove(); }
}
