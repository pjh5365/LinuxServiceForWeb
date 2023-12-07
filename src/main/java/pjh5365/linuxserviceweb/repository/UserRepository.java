package pjh5365.linuxserviceweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjh5365.linuxserviceweb.domain.user.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    UserEntity findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
