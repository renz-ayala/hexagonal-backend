package gg.users.userapps.domain.ports.in;

import gg.users.userapps.infraestructure.adapters.in.web.response.CreateUserResponse;

public interface CreateUser {
    CreateUserResponse execute(String username, String password);
}
