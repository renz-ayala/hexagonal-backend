package gg.users.userapps.application.usecase;

import gg.users.userapps.domain.model.Usuario;
import gg.users.userapps.domain.ports.in.CrearUsuario;
import gg.users.userapps.domain.ports.out.UsuarioRepository;
import gg.users.userapps.infraestructure.adapters.in.response.CrearUsuarioResponse;
import org.springframework.stereotype.Component;

@Component
public class CrearUsuarioUseCase implements CrearUsuario {

    private final UsuarioRepository usuarioRepository;

    public CrearUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public CrearUsuarioResponse ejecutar(String username, String password){
        CrearUsuarioResponse response = new CrearUsuarioResponse();
        boolean existeUsuario = usuarioRepository.existeUsuario(username);

        if (existeUsuario) {
            response.setMensaje("El usuario ya existe");
            response.setCodigo(1);
            return response;
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(password);

        usuarioRepository.crearUsuario(usuario);
        response.setMensaje("Usuario creado");
        response.setCodigo(2);

        return response;
    }
}
