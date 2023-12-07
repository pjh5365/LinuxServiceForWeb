package pjh5365.linuxserviceweb.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String email;
    private String password;
    private String secondaryCode;
    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)    // enum 을 데이터베이스에 저장하기 위한 어노테이션
    private UserRole role;

}
