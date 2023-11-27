package pjh5365.linuxserviceweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pjh5365.linuxserviceweb.dto.RegisterDto;
import pjh5365.linuxserviceweb.service.RegisterService;

@Controller
public class UserController {

    private final RegisterService registerService;

    @Autowired
    public UserController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/registerProc")
    public String registerProc(@ModelAttribute RegisterDto registerDto) {

        //TODO: 2023/11/27 회원가입 성공 및 실패에 자세한 처리필요
        if(registerService.register(registerDto))   // 회원가입에 성공했다면
            return "login";
        else
            return "register";   // 회원가입에 실패하면 재시도
    }
}
