package pjh5365.linuxserviceweb.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadLogServiceTest {

    @Test
    @DisplayName("로그파일을 정상적으로 불러 올 수 있는지 테스트")
    void ReadLogTest() throws IOException {
        // Given
        ReadLogService readLogService = new ReadLogService("/Users/parkjihyeok/serviceTest/");
        // When
        String test = readLogService.getLog("testLog.txt");
        // Then
        assertEquals(test, "Test log!!");
    }
}
