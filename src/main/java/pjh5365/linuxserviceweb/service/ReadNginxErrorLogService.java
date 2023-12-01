package pjh5365.linuxserviceweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pjh5365.linuxserviceweb.domain.log.NginxErrorLog;
import pjh5365.linuxserviceweb.domain.mail.Mail;
import pjh5365.linuxserviceweb.domain.user.UserEntity;
import pjh5365.linuxserviceweb.repository.UserRepository;

import java.util.Optional;

@Service
public class ReadNginxErrorLogService implements ReadLogService {

    private final UserRepository userRepository;

    @Autowired
    public ReadNginxErrorLogService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public NginxErrorLog[] loadLog() {
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> optionalUser = userRepository.findByUsername(authentication.getName());
        UserEntity user = optionalUser.get();

        this.copyLog(); // 파일 전송 전 백업부터

        Mail mail = new Mail();
        mail.sendLogMail(user.getEmail(), title, logFilePath);
    }
}
