package pjh5365.linuxserviceweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pjh5365.linuxserviceweb.domain.user.UserEntity;
import pjh5365.linuxserviceweb.dto.RegisterDto;
import pjh5365.linuxserviceweb.dto.UserDto;
import pjh5365.linuxserviceweb.repository.UserRepository;
import pjh5365.linuxserviceweb.service.RegisterService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {

    private final RegisterService registerService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    static Map<String, String > secondaryAuthMap;   // 1차 로그인에서 성공하면 1차 로그인 정보를 담을 맵
    static Map<String, String > errorMap;   // 1차 로그인에서 실패하면 해당 오류를 담을 맵

    @Autowired
    public UserController(RegisterService registerService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.registerService = registerService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        secondaryAuthMap = new HashMap<>(); // 빈으로 등록되면서 초기화
        secondaryAuthMap.put("status", "no");   // 초기화하며 최초 상태 설정
        errorMap = new HashMap<>(); // 빈으로 등록되면서 초기화
        errorMap.put("status", "ok");   // 초기화하며 최초 상태 설정
    }

    @GetMapping("/login")   // 로그인 실패에 대한 처리도 같이
    public String loginError(@RequestParam(value = "error", required = false) String error,
                             @RequestParam(value = "exception", required = false) String exception,
                             ModelMap map) {

        map.addAttribute("error", error);
        map.addAttribute("exception", exception);

        if(errorMap.get("status").matches("error")) {   // 1차 로그인에서 에러가 발생했다면
            map.addAttribute("error", errorMap.get("error"));
            map.addAttribute("exception", errorMap.get("exception"));
            errorMap.put("status", "ok");   // 맵의 상태는 초기값으로 변경
        }

        if(secondaryAuthMap.get("status").matches("ok")) {  // 1차 로그인에 성공하여 2차 로그인으로 넘어가야한다면
            map.addAttribute("secondaryAuth", secondaryAuthMap.get("secondaryAuth"));
            map.addAttribute("username", secondaryAuthMap.get("username"));
            map.addAttribute("password", secondaryAuthMap.get("password"));
            secondaryAuthMap.put("status", "no");   // 맵의 상태는 초기값으로 변경
        }

        return "login";
    }

    @PostMapping("/secondaryAuth")
    public String loginProc(@ModelAttribute UserDto userDto) {

        Optional<UserEntity> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if(optionalUser.isEmpty()) {    // 회원 정보를 찾지 못하는 경우
            errorMap.put("status", "error");
            errorMap.put("error", "true");
            errorMap.put("exception", "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.");
        }
        else {
            UserEntity user = optionalUser.get();

            if(bCryptPasswordEncoder.matches(userDto.getPassword(), user.getPassword())) {  // 비밀번호가 맞다면
                secondaryAuthMap.put("status", "ok");
                secondaryAuthMap.put("secondaryAuth", "auth");
                secondaryAuthMap.put("username", userDto.getUsername());
                secondaryAuthMap.put("password", userDto.getPassword());

                //TODO: 2023/12/1 인증번호 전송로직 추가
            }
            else {  // 비밀번호가 틀렸다면
                errorMap.put("status", "error");
                errorMap.put("error", "true");
                errorMap.put("exception", "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.");
            }
        }

        return "redirect:/login";
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
