package gg.users.userapps.application.usecase;

import gg.users.userapps.domain.model.User;
import gg.users.userapps.domain.ports.in.LoginUser;
import gg.users.userapps.domain.ports.out.JwtServicePort;
import gg.users.userapps.domain.ports.out.UserRepository;
import gg.users.userapps.infraestructure.adapters.in.web.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUserUseCase implements LoginUser {

    private final UserRepository userRepository;
    private final JwtServicePort jwtService;

    @Override
    public LoginResponse execute(String username, String password) {
        User user = userRepository.findUser(username, password);
        LoginResponse loginResponse = new LoginResponse();
        if (user != null) {
            String token = jwtService.generateToken(user);
            loginResponse.setToken(token);
            loginResponse.setMessage("Sesion valida");
        }else {
            loginResponse.setMessage("Las credenciales no son correctas");
        }
        return loginResponse;
    }
}
