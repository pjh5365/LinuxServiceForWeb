package pjh5365.linuxserviceweb.service;

import org.springframework.stereotype.Service;
import pjh5365.linuxserviceweb.domain.log.NginxErrorLog;
import pjh5365.linuxserviceweb.domain.mail.Mail;

@Service
public class ReadNginxErrorLogService implements ReadLogService {

    @Override
    public NginxErrorLog[] getLog() {
        return NginxErrorLog.loadLog();
    }

    @Override
    public void copyLog() {
        String copyPath = "/home/pibber/log/nginx/error/";     // 로그가 복사되어 저장될 경로
        NginxErrorLog nginxErrorLog = new NginxErrorLog();
        StringBuilder fileReader = NginxErrorLog.getLog();
        nginxErrorLog.copyLog(copyPath, fileReader);
    }

    @Override
    public void sendLog() {
        String title = "nginx error.log 파일 백업";
        String logFilePath = "/home/pibber/log/nginx/error/";

        Mail mail = new Mail();
        mail.sendLogMail(title, logFilePath);
    }
}
