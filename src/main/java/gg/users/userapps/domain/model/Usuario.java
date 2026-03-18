package gg.users.userapps.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Usuario {
    private Long cuentaId;
    private String username;
    private String password;
    private LocalDateTime tsCrea;

    public void validarNuevaPassword(String oldPassword, String newPassword) {
        if (newPassword == null || newPassword.length() < 8) {
            throw new IllegalArgumentException("La nueva contraseña debe tener al menos 8 caracteres.");
        }
        if (newPassword.equals(oldPassword)) {
            throw new IllegalArgumentException("La nueva contraseña no puede ser igual a la actual.");
        }
    }
}
