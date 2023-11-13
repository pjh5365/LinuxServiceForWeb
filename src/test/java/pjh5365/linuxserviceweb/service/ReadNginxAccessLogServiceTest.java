package pjh5365.linuxserviceweb.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pjh5365.linuxserviceweb.log.Log;
import pjh5365.linuxserviceweb.log.NginxAccessLog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadNginxAccessLogServiceTest {

    @Test
    @DisplayName("로그파일을 정상적으로 불러 올 수 있는지 테스트")
    void ReadLogTest() throws IOException {
        // Given
        ReadLogService readLogService = new ReadNginxAccessLogService();
        // When
        Log[] test = readLogService.getLog("access.log");
        // Then
        assertEquals(NginxAccessLog[].class, test.getClass());
    }

    @Test
    @DisplayName("읽어들인 로그파일을 다른 위치로 복사할 수 있는지 테스트")
    void CopyLogTest() throws IOException {
        // Given
        ReadLogService readLogService = new ReadNginxAccessLogService();
        String path = "/Users/parkjihyeok/testLog/access.log";  // 원래 로그파일의 경로
        readLogService.getLog("access.log");    // getLog 를 한번 호출해야 빌더에 값이 들어가므로 호출

        // When
        BufferedReader br = new BufferedReader(new FileReader(path));   // 원래 로그를 읽어서
        String st;
        StringBuilder expect = new StringBuilder();
        while((st = br.readLine()) != null) {   // 파일의 끝까지 읽기
            expect.append(st).append("\n");
        }
        StringBuilder sb = readLogService.copyLog("/nginx/access/");    // copy 한 로그의 StringBuilder 가져오기

        // Then
        assertEquals(expect.toString(), sb.toString()); // 복사된 값과 같은지 테스트
    }
}
