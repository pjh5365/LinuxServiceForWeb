package pjh5365.linuxserviceweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pjh5365.linuxserviceweb.dto.RegisterDto;
import pjh5365.linuxserviceweb.service.RegisterService;

@Controller
public class UserController {

    private final RegisterService registerService;

    @Autowired
    public UserController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/login")   // 로그인 실패에 대한 처리도 같이
    public String loginError(@RequestParam(value = "error", required = false) String error,
                             @RequestParam(value = "exception", required = false) String exception,
                             ModelMap map) {
        map.addAttribute("error", error);
        map.addAttribute("exception", exception);

        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerProc(@ModelAttribute RegisterDto registerDto, ModelMap map) {

        if(registerService.register(registerDto))   // 회원가입에 성공했다면
            return "login";
        else {
            map.addAttribute("error", "true");
            map.addAttribute("msg", "이미 존재하는 아이디입니다.");
            return "register";   // 회원가입에 실패하면 재시도
        }
    }
}
