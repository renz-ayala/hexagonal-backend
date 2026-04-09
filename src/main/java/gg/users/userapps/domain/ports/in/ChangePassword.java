package gg.users.userapps.domain.ports.in;

public interface ChangePassword {
    void execute(String oldPassword, String newPassword, String username);
}
