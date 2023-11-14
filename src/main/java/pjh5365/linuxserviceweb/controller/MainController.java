package pjh5365.linuxserviceweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import pjh5365.linuxserviceweb.log.NginxAccessLog;
import pjh5365.linuxserviceweb.service.ReadNginxAccessLogService;

@Controller
public class MainController {

    private final ReadNginxAccessLogService readLogService;

    @Autowired
    public MainController(ReadNginxAccessLogService readLogService) {
        this.readLogService = readLogService;
    }

    @GetMapping("/")
    public String index(ModelMap model) {
        try{    // 파일을 불러오지 못하는 경우를 대비하여 예외처리하기
            NginxAccessLog[] logs = readLogService.getLog();    // 읽어온 파일들을 ModelMap 에 넣어 전달하기

            //TODO: 2023/11/14 로그 복사와 전송 기능 분리하기
            readLogService.copyLog();   // 로그 복사하기
            readLogService.sendLog();   // 복사한 로그 메일로 전송하기

            model.addAttribute("logs", logs);
            return "index";
        } catch (RuntimeException e) {
            //TODO: 2023/11/14 파일을 불러오는데 실패하면 띄울 페이지 만들어서 이동시키기
            return "";
        }
    }
}
