package gg.users.userapps.infraestructure.adapters.in.web.request;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {}
