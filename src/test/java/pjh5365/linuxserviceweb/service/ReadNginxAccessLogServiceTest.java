package pjh5365.linuxserviceweb.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pjh5365.linuxserviceweb.log.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadNginxAccessLogServiceTest {

    @Test
    @DisplayName("로그파일을 정상적으로 불러 올 수 있는지 테스트")
    @Disabled   // 경로가 달라 테스트가 불가능하기 때문에 비활성화
    void ReadLogTest() {
        // Given
        ReadLogService readLogService = new ReadNginxAccessLogService();
        // When
        Log[] test = readLogService.getLog();
        // Then
        // 예외가 발생하지 않으면 성공
    }

    @Test
    @DisplayName("읽어들인 로그파일을 다른 위치로 복사할 수 있는지 테스트")
    @Disabled   // 경로가 달라 테스트가 불가능하기 때문에 비활성화
    void CopyLogTest() throws IOException {
        // Given
        ReadLogService readLogService = new ReadNginxAccessLogService();
        String path = "/Users/parkjihyeok/testLog/access.log";  // 원래 로그파일의 경로
        readLogService.getLog();    // getLog 를 한번 호출해야 빌더에 값이 들어가므로 호출

        // When
        BufferedReader br = new BufferedReader(new FileReader(path));   // 원래 로그를 읽어서
        String st;
        StringBuilder expect = new StringBuilder();
        while((st = br.readLine()) != null) {   // 파일의 끝까지 읽기
            expect.append(st).append("\n");
        }
        readLogService.copyLog();    // copy 한 로그의 StringBuilder 가져오기

        // Then
    }

    @Test
    @DisplayName("복사한 로그파일 메일전송 테스트")
    @Disabled   // 경로가 달라 테스트가 불가능하기 때문에 비활성화
    void copyLogAndSendMail() {
        // Given
        ReadLogService readLogService = new ReadNginxAccessLogService();
        // When
        readLogService.sendLog();
        // Then
        // 예외가 발생하지 않으면 성공
    }
}
