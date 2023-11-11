package pjh5365.linuxserviceweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import pjh5365.linuxserviceweb.service.ReadLogService;

import java.io.IOException;

@Controller
public class MainController {

    private final ReadLogService readLogService;
    private String fileName = "access.log";

    @Autowired
    public MainController(ReadLogService readLogService) {
        this.readLogService = readLogService;
    }

    @GetMapping("/")
    public String index(ModelMap model) throws IOException {
        model = readLogService.getLog(fileName);    // 빌더로 파일을 읽어온 후
        return "index";
    }
}
