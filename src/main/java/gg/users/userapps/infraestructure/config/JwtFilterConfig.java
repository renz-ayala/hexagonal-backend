package gg.users.userapps.infraestructure.config;

import gg.users.userapps.domain.ports.out.JwtServicePort;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class JwtFilterConfig extends OncePerRequestFilter {

    private final JwtServicePort jwtServicePort;

    public JwtFilterConfig(JwtServicePort jwtServicePort) {
        this.jwtServicePort = jwtServicePort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);

        try{
            Claims claims = jwtServicePort.validateToken(token);

            if(claims != null && SecurityContextHolder.getContext().getAuthentication() == null){
                String username = claims.getSubject();

                @SuppressWarnings("unchecked")
                List<String> roles = claims.get("permisos", List.class);

                Collection<SimpleGrantedAuthority> permisos = roles.stream().map(SimpleGrantedAuthority::new).toList();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, token, permisos);
                SecurityContextHolder.getContext().setAuthentication(auth);

            }else if(claims == null){
                this.tokenNoValido(response, "Token inválido o expirado");
                return;
            }

        } catch (Exception e) {
            this.tokenNoValido(response, "Error al validar la firma del token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void tokenNoValido(HttpServletResponse response, String message) throws IOException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
