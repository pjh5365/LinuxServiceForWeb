package pjh5365.linuxserviceweb.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SendMailServiceTest {

    @Test
    @DisplayName("메일전송 테스트")
    void sendMailTest() throws IOException {
        // Given
        SendMailService mailService = new SendMailService();
        // When
        String to = "pjh5365@naver.com";
        StringBuilder content = new StringBuilder("안녕하세요 메일 전송테스트입니다.");
        // Then
        mailService.sendMail(to, content);
    }
}
