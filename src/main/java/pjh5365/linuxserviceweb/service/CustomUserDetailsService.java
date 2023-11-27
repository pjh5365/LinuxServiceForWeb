package pjh5365.linuxserviceweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pjh5365.linuxserviceweb.domain.user.UserEntity;
import pjh5365.linuxserviceweb.dto.CustomUserDetails;
import pjh5365.linuxserviceweb.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = new UserEntity();

        user = userRepository.findByUsername(username);

        if(user != null)    // 로그인 정보가 존재한다면
            return new CustomUserDetails(user);

        //TODO: 2023/11/27 로그인 실패에 대한 처리도 필요함
        return null;
    }
}
