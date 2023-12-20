package pjh5365.linuxserviceweb.domain.auth.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pjh5365.linuxserviceweb.domain.auth.GenerateCode;
import pjh5365.linuxserviceweb.domain.mail.Mail;
import pjh5365.linuxserviceweb.domain.user.UserEntity;
import pjh5365.linuxserviceweb.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Getter
@Service
public class SecondaryAuthService {

    // 2. 생성된 코드를 필터에서 가져다 쓰기
    private String code;
    private final UserRepository userRepository;
    private final Mail mail;

    @Autowired
    public SecondaryAuthService(UserRepository userRepository, Mail mail) {
        this.userRepository = userRepository;
        this.mail = mail;
    }

   @Async("threadPool")    // 쓰레드 풀을 사용하기 위해 설정한 빈을 사용
    public void sendSecondaryAuth(String email) {    // 1. 해당 메서드가 호출되면 코드를 생성하고
        code = GenerateCode.generate();

        mail.sendEmailAuth(email, code);
        UserEntity user = userRepository.findByEmail(email);
        user.setSecondaryCode(code);    // 인증코드 설정
        user.setExpiredAt(LocalDateTime.now().plusMinutes(5));  // 유효기간은 지금으로 부터 +5분
        userRepository.save(user);  // 인증코드와 유효기간 DB 에 업로드
    }

    public boolean checkSecondaryCode(String username, String code) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty()) {    // 사용자 정보를 찾지 못하는 경우
            return false;
        }
        else {
            UserEntity user = optionalUser.get();
            // 유효기간과 인증코드를 확인한 결과를 리턴
            return user.getSecondaryCode().matches(code) && LocalDateTime.now().isBefore(user.getExpiredAt());
        }
    }
}
