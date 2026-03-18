package gg.users.userapps.application.usecase;

import gg.users.userapps.domain.model.Usuario;
import gg.users.userapps.domain.ports.in.CambiarContrasenia;
import gg.users.userapps.domain.ports.out.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class CambiarContraseniaUseCase implements CambiarContrasenia {

    private final UsuarioRepository usuarioRepository;

    public CambiarContraseniaUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void ejecutar(String oldPassword, String newPassword, String username){
        Usuario usuario = new Usuario();
        usuario.validarNuevaPassword(oldPassword, newPassword);
        usuarioRepository.cambiarContrasenia(username, oldPassword, newPassword);
    }
}
