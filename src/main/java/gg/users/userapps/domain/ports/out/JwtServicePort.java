package gg.users.userapps.domain.ports.out;

import gg.users.userapps.domain.model.User;
import io.jsonwebtoken.Claims;

public interface JwtServicePort {
    String generateToken(User user);
    Claims validateToken(String token);
}
