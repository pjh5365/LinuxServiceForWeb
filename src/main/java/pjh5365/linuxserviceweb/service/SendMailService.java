package pjh5365.linuxserviceweb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pjh5365.linuxserviceweb.domain.mail.Mail;

@Slf4j
@Service
public class SendMailService {

    private final Mail mail;
    private String path = "/home/pibber/sendmail/";
    private String mailFile = "mail.txt";

    @Autowired
    public SendMailService(Mail mail) {
        this.mail = mail;
    }

    public void sendMail(String to, String title, StringBuilder content) {
        mail.sendNormalMail(to, title, content, path, mailFile);
    }
}
