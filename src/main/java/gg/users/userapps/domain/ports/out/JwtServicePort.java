package gg.users.userapps.domain.ports.out;

import gg.users.userapps.domain.model.Usuario;
import io.jsonwebtoken.Claims;

public interface JwtServicePort {
    String generarToken(Usuario user);
    Claims validarToken(String token);
}
