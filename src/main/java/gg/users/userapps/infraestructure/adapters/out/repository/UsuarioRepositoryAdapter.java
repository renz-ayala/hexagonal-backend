package gg.users.userapps.infraestructure.adapters.out.repository;

import gg.users.userapps.domain.model.Usuario;
import gg.users.userapps.domain.ports.out.UsuarioRepository;
import gg.users.userapps.infraestructure.adapters.out.entities.UsuarioEntity;
import gg.users.userapps.infraestructure.adapters.out.repository.jpa.UsuarioJpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final UsuarioJpaRepository usuarioJpaRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository usuarioJpaRepository,
                                    BCryptPasswordEncoder passwordEncoder) {
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        String hash = passwordEncoder.encode(usuario.getPassword());

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setUsername(usuario.getUsername());
        usuarioEntity.setPassword(hash);
        UsuarioEntity newUserEntity = usuarioJpaRepository.save(usuarioEntity);

        return this.mapToDomain(newUserEntity);

    }

    @Override
    @Transactional
    public Usuario buscarUsuario(String username, String password){
        Optional<UsuarioEntity> userEntity = usuarioJpaRepository.findByUsername(username);
        Usuario usuario = null;
        if (userEntity.isPresent()) {
            UsuarioEntity usuarioEntity = userEntity.get();
            if(passwordEncoder.matches(password, usuarioEntity.getPassword())) {
                usuario = this.mapToDomain(usuarioEntity);
            }
        }
        return usuario;
    }

    @Override
    @Transactional
    public boolean existeUsuario(String username){
        Optional<UsuarioEntity> userEntity = usuarioJpaRepository.findByUsername(username);
        return userEntity.isPresent();
    }

    @Override
    @Transactional
    public void cambiarContrasenia(String username, String oldPassword, String newPassword){
        Optional<UsuarioEntity> userEntity = usuarioJpaRepository.findByUsername(username);
        if(userEntity.isPresent()){
            UsuarioEntity usuarioEntity = userEntity.get();
            if(!passwordEncoder.matches(oldPassword, usuarioEntity.getPassword())){
                throw new RuntimeException("La contraseña actual es incorrecta");
            }
            usuarioEntity.setPassword(passwordEncoder.encode(newPassword));
            usuarioJpaRepository.save(usuarioEntity);
        }else{
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    private Usuario mapToDomain(UsuarioEntity usuarioEntity) {
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioEntity.getUsername());
        usuario.setPassword(usuarioEntity.getPassword());
        usuario.setCuentaId(usuarioEntity.getCuentaId());
        usuario.setTsCrea(usuarioEntity.getTsCrea());
        return usuario;
    }
}
