package pjh5365.linuxserviceweb.domain.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pjh5365.linuxserviceweb.domain.user.UserEntity;
import pjh5365.linuxserviceweb.domain.auth.CustomUserDetails;
import pjh5365.linuxserviceweb.repository.UserRepository;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("해당 사용자 정보를 찾을 수 없습니다. username : {}", username);
                    return new UsernameNotFoundException("해당 사용자 정보를 찾을 수 없습니다." + username);   // 사용자 정보를 찾을 수 없다면 예외를 터트림
                });

        return new CustomUserDetails(user); // 사용자 정보가 있다면 리턴
    }
}
