package gg.users.userapps.domain.ports.out;

import gg.users.userapps.domain.model.User;

public interface UserRepository {
    void createUser(User user);
    User findUser(String username, String password);
    boolean userExists(String username);
    void changePassword(String username, String oldPassword, String newPassword);
}
