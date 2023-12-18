package pjh5365.linuxserviceweb.domain.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Component
public class EmailMessageConsumer {

    @RabbitListener(queues = "Email-auth")
    public void consumer(MailMessageDto messageDto) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(messageDto.getPath()));    // 메일을 보내기전에 보낼 내용을 다른 파일에 미리 저장하고
            bufferedWriter.write("To: " + messageDto.getTo());
            bufferedWriter.newLine();
            bufferedWriter.write("Subject: " + messageDto.getSubject());
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write(messageDto.getContent());
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error("메일 전송에 필요한 파일을 열지 못했습니다. : {}", e.getMessage());
            throw new RuntimeException(e);
        }

        String[] cmd = {"/bin/sh", "-c", "sendmail -t < " + messageDto.getPath()};
        try {
            Runtime.getRuntime().exec(cmd);   // 저장된 파일을 이용해 메일을 전송한다.
        } catch (IOException e) {
            log.error("메일 전송 스크립트 실행에 실패해 메일을 전송하지 못했습니다. : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
