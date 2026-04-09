package gg.users.userapps.domain.ports.in;

import gg.users.userapps.infraestructure.adapters.in.web.response.LoginResponse;

public interface LoginUser {
    LoginResponse execute(String username, String password);
}
