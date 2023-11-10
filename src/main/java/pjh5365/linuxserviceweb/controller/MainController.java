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
    private String fileName = "testLog.txt";

    @Autowired
    public MainController(ReadLogService readLogService) {
        this.readLogService = readLogService;
    }

    @GetMapping("/")
    public String index(ModelMap model) throws IOException {
        StringBuilder sb = readLogService.getLog(fileName);    // 빌더로 파일을 읽어온 후
        String[] lines = sb.toString().split("\n"); // 배열로 한줄로 저장하여
        model.addAttribute("logs", lines);  // 타임리프로 보낸다.
        return "index";
    }
}
