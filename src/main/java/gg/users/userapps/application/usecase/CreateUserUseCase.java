package gg.users.userapps.application.usecase;

import gg.users.userapps.domain.model.User;
import gg.users.userapps.domain.ports.in.CreateUser;
import gg.users.userapps.domain.ports.out.UserRepository;
import gg.users.userapps.infraestructure.adapters.in.web.response.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUser {

    private final UserRepository userRepository;

    @Override
    public CreateUserResponse execute(String username, String password){
        CreateUserResponse response = new CreateUserResponse();
        User user = new User();
        user.validateUsername(username);
        user.validatePassword(password);

        boolean userExists = userRepository.userExists(username);

        if (userExists) {
            response.setMessage("El usuario ya existe");
            response.setCode(1);
            return response;
        }


        user.setUsername(username);
        user.setPassword(password);
        userRepository.createUser(user);

        response.setMessage("Usuario creado");
        response.setCode(2);

        // 1 not created, 2 created
        return response;
    }
}
