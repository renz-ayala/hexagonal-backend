package gg.users.userapps.application.usecase;

import gg.users.userapps.domain.model.Usuario;
import gg.users.userapps.domain.ports.in.LoginUsuario;
import gg.users.userapps.domain.ports.out.JwtServicePort;
import gg.users.userapps.domain.ports.out.UsuarioRepository;
import gg.users.userapps.infraestructure.adapters.in.response.LoginResponse;
import org.springframework.stereotype.Component;

@Component
public class LoginUsuarioUseCase implements LoginUsuario {

    private final UsuarioRepository usuarioRepository;
    private final JwtServicePort jwtService;

    public LoginUsuarioUseCase(UsuarioRepository usuarioRepository,
                               JwtServicePort jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse ejecutar(String username, String password) {
        Usuario user = usuarioRepository.buscarUsuario(username, password);
        LoginResponse loginResponse = new LoginResponse();
        if (user != null) {
            String token = jwtService.generarToken(user);
            loginResponse.setToken(token);
            loginResponse.setMessage("Sesion valida");
        }else {
            loginResponse.setMessage("Las credenciales no son correctas");
        }
        return loginResponse;
    }
}
