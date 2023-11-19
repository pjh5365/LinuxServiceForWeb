package pjh5365.linuxserviceweb.service;

import org.springframework.stereotype.Service;
import pjh5365.linuxserviceweb.domain.log.MessagesLog;
import pjh5365.linuxserviceweb.domain.mail.Mail;

@Service
public class ReadMessagesLogService implements ReadLogService{
    @Override
    public MessagesLog[] loadLog() {
        return MessagesLog.loadLog();
    }

    @Override
    public void copyLog() {
        String copyPath = "/home/pibber/log/messages/";     // 로그가 복사되어 저장될 경로
        MessagesLog messagesLog = new MessagesLog();
        StringBuilder fileReader = MessagesLog.getLog();
        messagesLog.copyLog(copyPath, fileReader);
    }

    @Override
    public void sendLog() {
        String title = "messages 파일 백업";
        String logFilePath = "/home/pibber/log/messages/";

        Mail mail = new Mail();
        mail.sendLogMail(title, logFilePath);
    }
}
