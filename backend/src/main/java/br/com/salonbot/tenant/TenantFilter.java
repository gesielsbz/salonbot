package br.com.salonbot.tenant;
import br.com.salonbot.config.TenantProperties;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.UUID;
@Component
@Profile("!production")
public class TenantFilter extends OncePerRequestFilter {
  private final TenantProperties properties;
  public TenantFilter(TenantProperties properties) { this.properties = properties; }
  @Override protected boolean shouldNotFilter(HttpServletRequest request) {
    String p = request.getRequestURI();
    return p.startsWith("/actuator") || p.startsWith("/api/v1/webhooks/");
  }
  @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws ServletException, IOException {
    try {
      String header = req.getHeader("X-Tenant-Id");
      UUID id = header == null || header.isBlank() ? properties.devTenantId() : UUID.fromString(header);
      TenantContext.set(id);
      chain.doFilter(req, res);
    } catch (IllegalArgumentException ex) {
      res.sendError(400, "X-Tenant-Id must be a UUID");
    } finally { TenantContext.clear(); }
  }
}
