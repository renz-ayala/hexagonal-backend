package gg.users.userapps.infraestructure.adapters.in.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambiarPasswordRequest {
    private String oldPassword;
    private String newPassword;
}
