package gg.users.userapps.domain.ports.in;

import gg.users.userapps.infraestructure.adapters.in.response.CrearUsuarioResponse;

public interface CrearUsuario {
    CrearUsuarioResponse ejecutar(String username, String password);
}
