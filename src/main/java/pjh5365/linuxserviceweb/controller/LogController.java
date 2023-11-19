package pjh5365.linuxserviceweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pjh5365.linuxserviceweb.domain.log.NginxAccessLog;
import pjh5365.linuxserviceweb.domain.log.NginxErrorLog;
import pjh5365.linuxserviceweb.service.ReadNginxAccessLogService;
import pjh5365.linuxserviceweb.service.ReadNginxErrorLogService;

@Controller
@RequestMapping("/log")
public class LogController {


    private final ReadNginxAccessLogService accessLogService;
    private final ReadNginxErrorLogService errorLogService;

    @Autowired
    public LogController(ReadNginxAccessLogService accessLogService, ReadNginxErrorLogService errorLogService) {
        this.accessLogService = accessLogService;
        this.errorLogService = errorLogService;
    }

    @GetMapping("/nginx/access")
    public String nginxAccessLog(ModelMap model) {
        try{    // 파일을 불러오지 못하는 경우를 대비하여 예외처리하기
            NginxAccessLog[] logs = accessLogService.getLog();    // 읽어온 파일들을 ModelMap 에 넣어 전달하기

            //TODO: 2023/11/14 로그 복사와 전송 기능 분리하기
            accessLogService.copyLog();   // 로그 복사하기
            accessLogService.sendLog();   // 복사한 로그 메일로 전송하기

            model.addAttribute("logs", logs);
            return "nginx-access";
        } catch (RuntimeException e) {
            //TODO: 2023/11/14 파일을 불러오는데 실패하면 띄울 페이지 만들어서 이동시키기
            return "index";
        }
    }

    @GetMapping("/nginx/error")
    public String nginxErrorLog(ModelMap model) {
        try {
            NginxErrorLog[] logs = errorLogService.getLog();

            errorLogService.copyLog();
            errorLogService.sendLog();

            model.addAttribute("logs", logs);
            return "nginx-error";
        } catch (RuntimeException e) {
            return "index";
        }
    }
}
