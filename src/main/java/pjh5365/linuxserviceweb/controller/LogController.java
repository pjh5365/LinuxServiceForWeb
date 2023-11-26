package pjh5365.linuxserviceweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pjh5365.linuxserviceweb.domain.log.MessagesLog;
import pjh5365.linuxserviceweb.domain.log.NginxAccessLog;
import pjh5365.linuxserviceweb.domain.log.NginxErrorLog;
import pjh5365.linuxserviceweb.service.ReadMessagesLogService;
import pjh5365.linuxserviceweb.service.ReadNginxAccessLogService;
import pjh5365.linuxserviceweb.service.ReadNginxErrorLogService;

@Slf4j
@Controller
@RequestMapping("/log")
public class LogController {


    private final ReadNginxAccessLogService accessLogService;
    private final ReadNginxErrorLogService errorLogService;
    private final ReadMessagesLogService messagesLogService;

    @Autowired
    public LogController(ReadNginxAccessLogService accessLogService, ReadNginxErrorLogService errorLogService, ReadMessagesLogService messagesLogService) {
        this.accessLogService = accessLogService;
        this.errorLogService = errorLogService;
        this.messagesLogService = messagesLogService;
    }

    @GetMapping("/nginx/access")
    public String nginxAccessLog(ModelMap map) {
        try{
            NginxAccessLog[] logs = accessLogService.loadLog();    // 읽어온 파일들을 ModelMap 에 넣어 전달하기
            map.addAttribute("logs", logs);
            return "nginx-access";
        } catch (RuntimeException e) {
            map.addAttribute("msg", "파일불러오는데 실패하여 메인페이지로 이동합니다.");
            log.error("파일을 불러오는데 실패했습니다. : {}", e.getMessage());
            return "pageAction";
        }
    }

    @GetMapping("/nginx/error")
    public String nginxErrorLog(ModelMap map) {
        try {
            NginxErrorLog[] logs = errorLogService.loadLog();
            map.addAttribute("logs", logs);
            return "nginx-error";
        } catch (RuntimeException e) {
            map.addAttribute("msg", "파일불러오는데 실패하여 메인페이지로 이동합니다.");
            log.error("파일을 불러오는데 실패했습니다. : {}", e.getMessage());
            return "pageAction";
        }
    }

    @GetMapping("/messages")
    public String messagesLog(ModelMap map) {
        try {
            MessagesLog[] logs = messagesLogService.loadLog();
            map.addAttribute("logs", logs);
            return "messages";
        } catch (RuntimeException e) {
            map.addAttribute("msg", "파일불러오는데 실패하여 메인페이지로 이동합니다.");
            log.error("파일을 불러오는데 실패했습니다. : {}", e.getMessage());
            return "pageAction";
        }
    }

    @GetMapping("/nginx/access/copy")
    public String copyNginxAccess(ModelMap map) {
        try {
            accessLogService.copyLog();
            map.addAttribute("msg", "파일백업에 성공하였습니다.");
        } catch (RuntimeException e) {
            map.addAttribute("msg", "파일백업에 실패하여 메인페이지로 이동합니다.");
            log.error("파일백업에 실패했습니다. : {}", e.getMessage());
        }
        return "pageAction";
    }
    @GetMapping("/nginx/error/copy")
    public String copyNginxError(ModelMap map) {
        try {
            errorLogService.copyLog();
            map.addAttribute("msg", "파일백업에 성공하였습니다.");
        } catch (RuntimeException e) {
            map.addAttribute("msg", "파일백업에 실패하여 메인페이지로 이동합니다.");
            log.error("파일백업에 실패했습니다. : {}", e.getMessage());
        }
        return "pageAction";
    }
    @GetMapping("/messages/copy")
    public String copyMessages(ModelMap map) {
        try {
            messagesLogService.copyLog();
            map.addAttribute("msg", "파일백업에 성공하였습니다.");
        } catch (RuntimeException e) {
            map.addAttribute("msg", "파일백업에 실패하여 메인페이지로 이동합니다.");
            log.error("파일백업에 실패했습니다. : {}", e.getMessage());
        }
        return "pageAction";
    }

    @GetMapping("/nginx/access/send")
    public String sendNginxAccess(ModelMap map) {
        try {
            accessLogService.sendLog();
            map.addAttribute("msg", "파일전송에 성공하였습니다.");
        } catch (RuntimeException e) {
            map.addAttribute("msg", "파일전송에 실패하여 메인페이지로 이동합니다.");
            log.error("파일전송에 실패했습니다. {}", e.getMessage());
        }
        return "pageAction";
    }
    @GetMapping("/nginx/error/send")
    public String sendNginxError(ModelMap map) {
        try {
            errorLogService.sendLog();
            map.addAttribute("msg", "파일전송에 성공하였습니다.");
        } catch (RuntimeException e) {
            map.addAttribute("msg", "파일전송에 실패하여 메인페이지로 이동합니다.");
            log.error("파일전송에 실패했습니다. {}", e.getMessage());
        }
        return "pageAction";
    }
    @GetMapping("/messages/send")
    public String sendMessages(ModelMap map) {
        try {
            messagesLogService.sendLog();
            map.addAttribute("msg", "파일전송에 성공하였습니다.");
        } catch (RuntimeException e) {
            map.addAttribute("msg", "파일전송에 실패하여 메인페이지로 이동합니다.");
            log.error("파일전송에 실패했습니다. {}", e.getMessage());
        }
        return "pageAction";
    }
}
