package pjh5365.linuxserviceweb.domain.log;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class Log {
    public void copyLog(String copyPath, StringBuilder fileContent) {

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년MM월dd일");
        String fileName = now.format(formatter) + ".log";    // 로그는 현재 날짜를 파일명으로 하여 저장한다.

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(copyPath + fileName));
            bufferedWriter.write(String.valueOf(fileContent));
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error("복사할 경로 \"{}\" 를 여는데 실패했습니다.", copyPath);
            throw new RuntimeException(e);
        }
    }
}
