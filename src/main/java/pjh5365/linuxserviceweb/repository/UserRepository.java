package pjh5365.linuxserviceweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjh5365.linuxserviceweb.domain.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    boolean existsByUsername(String username);
}
