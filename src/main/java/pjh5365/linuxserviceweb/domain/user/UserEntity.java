package pjh5365.linuxserviceweb.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)    // enum 을 데이터베이스에 저장하기 위한 어노테이션
    private UserRole role;

}
