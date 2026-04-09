package gg.users.userapps.infraestructure.adapters.in.web.request;

public record LoginRequest(
        String username,
        String password
) {}
