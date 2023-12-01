package pjh5365.linuxserviceweb.domain.mail;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class Mail {

    private String to;

    public void sendNormalMail(String to, String title, StringBuilder content , String path, String mailFile) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path + mailFile));    // 메일을 보내기전에 보낼 내용을 다른 파일에 미리 저장하고
            bufferedWriter.write("To: " + to);
            bufferedWriter.newLine();
            bufferedWriter.write("Subject: " +title);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(content));
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error("메일 전송에 필요한 파일을 열지 못했습니다. : {}", e.getMessage());
            throw new RuntimeException(e);
        }

        String[] cmd = {"/bin/sh", "-c", "sendmail -t < " + path + mailFile};
        try {
            Runtime.getRuntime().exec(cmd);   // 저장된 파일을 이용해 메일을 전송한다.
        } catch (IOException e) {
            log.error("메일 전송 스크립트 실행에 실패해 메일을 전송하지 못했습니다. : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void sendLogMail(String to, String title, String logFilePath) {
        String mailPath = "/home/pibber/log/";  // 실행될 스크립트의 경로

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년MM월dd일");
        String fileName = now.format(formatter) + ".log";    // 전송할 로그는 현재 날짜로 이름을 가지고 있다.

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(mailPath + "sendmail.sh"));  // 스크립트 파일 작성
            bufferedWriter.write("recipient=\"" + to + "\"\n" +
                    "subject=\"" + now.format(formatter) + " " + title + "\" \n" +    // 전송될 메일의 제목을 외부로부터 주입받아 사용
                    "body=\"첨부파일 참고\"\n" +
                    "attachment=\"" + logFilePath + fileName + "\"\n" + // 첨부파일의 경로를 주입받아 사용
                    "\n" +
                    "mutt -s \"$subject\" -a \"$attachment\" -- \"$recipient\" <<EOL\n" +   // mutt 명령을 사용한 메일전송
                    "$body\n" +
                    "EOL\n");
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error("로그를 메일로 전송하기 위한 사전세팅에 실패했습니다. : {}", e.getMessage());
            throw new RuntimeException(e);
        }

        String[] cmd = {"/bin/sh", "-c", "sh " + mailPath + "sendmail.sh"}; // 스크립트 파일을 실행하기 위한 코드
        try {
            Runtime.getRuntime().exec(cmd); // 스크립트 파일 실행
        } catch (IOException e) {
            log.error("로그메일 전송 스크립트 실행에 실패해 메일을 전송하지 못했습니다. : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
