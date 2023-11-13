package pjh5365.linuxserviceweb.service;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class SendMailService {

    private String path = "/home/pibber/sendmail/";
    private String mailFile = "mail.txt";
    public void sendMail(String to, String title, StringBuilder content) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path + mailFile));    // 메일을 보내기전에 보낼 내용을 다른 파일에 미리 저장하고

        bufferedWriter.write("To: " + to);
        bufferedWriter.newLine();
        bufferedWriter.write("Subject: " +title);
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write(String.valueOf(content));
        bufferedWriter.flush();

        // 저장된 파일을 이용해 메일을 전송한다.
        String[] cmd = {"/bin/sh", "-c", "sendmail -t < " + path + mailFile};
        Runtime.getRuntime().exec(cmd);
    }
}
