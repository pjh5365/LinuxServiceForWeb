package pjh5365.linuxserviceweb.domain.auth.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pjh5365.linuxserviceweb.domain.auth.GenerateCode;
import pjh5365.linuxserviceweb.domain.mail.Mail;

@Slf4j
@Getter
@Service
public class SecondaryAuthService {

    // 2. 생성된 코드를 필터에서 가져다 쓰기
    private String code;

    public void sendSecondaryAuth(String email) {    // 1. 해당 메서드가 호출되면 코드를 생성하고
        Mail mail = new Mail();
        code = GenerateCode.generate();
        try {
            mail.sendEmailAuth(email, code);
        } catch (Exception e) {
            log.error("2차 로그인에 필요한 인증코드를 전송하는데 실패했습니다. : {}", e.getMessage());
            throw new RuntimeException();
        }
    }
}
