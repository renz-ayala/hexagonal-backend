package gg.users.userapps.domain.ports.out;

import gg.users.userapps.domain.model.Usuario;
import gg.users.userapps.infraestructure.adapters.out.entities.UsuarioEntity;

import java.util.Optional;

public interface UsuarioRepository {
    Usuario crearUsuario(Usuario usuario);
    Usuario buscarUsuario(String username, String password);
    boolean existeUsuario(String username);
    void cambiarContrasenia(String username, String oldPassword, String newPassword);
}
