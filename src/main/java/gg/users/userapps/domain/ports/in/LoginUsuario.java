package gg.users.userapps.domain.ports.in;

import gg.users.userapps.infraestructure.adapters.in.response.LoginResponse;

public interface LoginUsuario {
    LoginResponse ejecutar(String username, String password);
}
