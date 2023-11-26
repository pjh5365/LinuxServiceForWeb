package pjh5365.linuxserviceweb.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SendMailServiceTest {

    @Test
    @DisplayName("메일전송 테스트")
    @Disabled   // 경로가 달라 테스트가 불가능하기 때문에 비활성화
    void sendMailTest() throws Exception {
        // Given
        SendMailService mailService = new SendMailService();
        // When
        String to = "pjh5365@naver.com";
        String title = "메일 전송테스트 제목입니다.";
        StringBuilder content = new StringBuilder("안녕하세요 메일 전송테스트입니다.");
        // Then
        mailService.sendMail(to, title, content);
    }
}
