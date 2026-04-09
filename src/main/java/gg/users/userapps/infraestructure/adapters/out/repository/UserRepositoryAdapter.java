package gg.users.userapps.infraestructure.adapters.out.repository;

import gg.users.userapps.domain.model.User;
import gg.users.userapps.domain.ports.out.UserRepository;
import gg.users.userapps.infraestructure.adapters.out.repository.entities.UserEntity;
import gg.users.userapps.infraestructure.adapters.out.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(User user) {
        String hash = passwordEncoder.encode(user.getPassword());

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(hash);
        UserEntity newUserEntity = userJpaRepository.save(userEntity);

        this.mapToDomain(newUserEntity);

    }

    @Override
    @Transactional
    public User findUser(String username, String password){
        Optional<UserEntity> userEntity = userJpaRepository.findByUsername(username);
        User user = null;
        if (userEntity.isPresent()) {
            UserEntity userPresent = userEntity.get();
            if(passwordEncoder.matches(password, userPresent.getPassword())) {
                user = this.mapToDomain(userPresent);
            }
        }
        return user;
    }

    @Override
    @Transactional
    public boolean userExists(String username){
        Optional<UserEntity> userEntity = userJpaRepository.findByUsername(username);
        return userEntity.isPresent();
    }

    @Override
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword){
        Optional<UserEntity> userEntity = userJpaRepository.findByUsername(username);
        if(userEntity.isPresent()){
            UserEntity userPresent = userEntity.get();
            if(!passwordEncoder.matches(oldPassword, userPresent.getPassword())){
                throw new RuntimeException("La contraseña actual es incorrecta");
            }
            userPresent.setPassword(passwordEncoder.encode(newPassword));
            userJpaRepository.save(userPresent);
        }else{
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    private User mapToDomain(UserEntity userEntity) {
        User user = new User();
        user.setUsername(userEntity.getUsername());
        user.setPassword(userEntity.getPassword());
        user.setAccountId(userEntity.getCuentaId());
        user.setTsCrea(userEntity.getTsCrea());
        return user;
    }
}
