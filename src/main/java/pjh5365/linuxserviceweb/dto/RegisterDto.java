package pjh5365.linuxserviceweb.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RegisterDto {

    private String name;
    private String username;
    private String email;
    private String password;
}
