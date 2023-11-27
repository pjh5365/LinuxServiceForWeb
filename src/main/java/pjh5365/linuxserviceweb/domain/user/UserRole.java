package pjh5365.linuxserviceweb.domain.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
