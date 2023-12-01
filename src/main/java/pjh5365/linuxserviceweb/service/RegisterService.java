package pjh5365.linuxserviceweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pjh5365.linuxserviceweb.domain.user.UserEntity;
import pjh5365.linuxserviceweb.domain.user.UserRole;
import pjh5365.linuxserviceweb.dto.RegisterDto;
import pjh5365.linuxserviceweb.repository.UserRepository;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegisterService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean register(RegisterDto data) {
        UserEntity userEntity = new UserEntity();
        boolean isRegistered =  userRepository.existsByUsername(data.getUsername()) && userRepository.existsByEmail(data.getEmail());

        if(isRegistered)    // 회원정보가 이미 존재한다면
            return false;

        userEntity.setName(data.getName());
        userEntity.setUsername(data.getUsername());
        userEntity.setEmail(data.getEmail());
        userEntity.setPassword(bCryptPasswordEncoder.encode(data.getPassword()));
        userEntity.setRole(UserRole.ROLE_USER);

        userRepository.save(userEntity);
        return true;
    }
}
