package pjh5365.linuxserviceweb.service;

import org.springframework.stereotype.Service;
import pjh5365.linuxserviceweb.domain.log.NginxAccessLog;
import pjh5365.linuxserviceweb.domain.mail.Mail;

@Service
public class ReadNginxAccessLogService implements ReadLogService {

    @Override
    public NginxAccessLog[] getLog() {
        return NginxAccessLog.loadLog();
    }

    @Override
    public void copyLog() {
        String copyPath = "/home/pibber/log/nginx/access/";     // 로그가 복사되어 저장될 경로
        NginxAccessLog nginxAccessLog = new NginxAccessLog();
        StringBuilder fileReader = NginxAccessLog.getLog();
        nginxAccessLog.copyLog(copyPath, fileReader);
    }

    @Override
    public void sendLog() {
        String title = "nginx access.log 파일 백업";
        String logFilePath = "/home/pibber/log/nginx/access/";

        Mail mail = new Mail();
        mail.sendLogMail(title, logFilePath);
    }
}
