package gg.users.userapps.infraestructure.adapters.in.controller;

import gg.users.userapps.domain.ports.in.CambiarContrasenia;
import gg.users.userapps.domain.ports.in.CrearUsuario;
import gg.users.userapps.domain.ports.in.LoginUsuario;
import gg.users.userapps.infraestructure.adapters.in.request.CambiarPasswordRequest;
import gg.users.userapps.infraestructure.adapters.in.request.CrearUsuarioRequest;
import gg.users.userapps.infraestructure.adapters.in.response.CrearUsuarioResponse;
import gg.users.userapps.infraestructure.adapters.in.response.LoginResponse;
import org.springframework.security.core.Authentication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final CrearUsuario crearUsuario;
    private final LoginUsuario loginUsuario;
    private final CambiarContrasenia cambiarContrasenia;

    public UsuarioController(CrearUsuario crearUsuario,
                             LoginUsuario loginUsuario,
                             CambiarContrasenia cambiarContrasenia) {
        this.crearUsuario = crearUsuario;
        this.loginUsuario = loginUsuario;
        this.cambiarContrasenia = cambiarContrasenia;
    }

    @PostMapping(value = "/public/crear-usuario", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CrearUsuarioResponse crearUsuario(@RequestBody CrearUsuarioRequest data) {
        return crearUsuario.ejecutar(data.getUsername(), data.getPassword());
    }

    @PostMapping(value = "/public/iniciar-sesion",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse iniciarSesion(@RequestBody CrearUsuarioRequest data) {
        return loginUsuario.ejecutar(data.getUsername(), data.getPassword());
    }

    @PostMapping(value = "/cambiar-password", consumes = "application/json", produces = "application/json")
    public void cambiarPassword(@RequestBody CambiarPasswordRequest data, Authentication authentication) {
        cambiarContrasenia.ejecutar(data.getOldPassword(), data.getNewPassword(), authentication.getName());
    }
}
