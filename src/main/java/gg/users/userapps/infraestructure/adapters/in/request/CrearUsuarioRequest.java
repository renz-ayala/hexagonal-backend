package gg.users.userapps.infraestructure.adapters.in.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearUsuarioRequest {
    private String username;
    private String password;
}
