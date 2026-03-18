package gg.users.userapps.domain.ports.in;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

public interface CambiarContrasenia {
    void ejecutar(String oldPassword, String newPassword, String username);
}
