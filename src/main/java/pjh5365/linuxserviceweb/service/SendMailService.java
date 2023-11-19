package pjh5365.linuxserviceweb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pjh5365.linuxserviceweb.domain.mail.Mail;

@Slf4j
@Service
public class SendMailService {

    private String path = "/home/pibber/sendmail/";
    private String mailFile = "mail.txt";
    public void sendMail(String to, String title, StringBuilder content) {
        Mail mail = new Mail();
        mail.sendNormalMail(to, title, content, path, mailFile);
    }
}
