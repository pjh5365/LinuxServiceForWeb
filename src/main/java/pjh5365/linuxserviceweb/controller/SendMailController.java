package pjh5365.linuxserviceweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pjh5365.linuxserviceweb.dto.Mail;
import pjh5365.linuxserviceweb.service.SendMailService;

import java.io.IOException;

@Controller
@RequestMapping("/sendmail")
public class SendMailController {

    private final SendMailService sendMailService;

    public SendMailController(SendMailService sendMailService) {
        this.sendMailService = sendMailService;
    }

    @GetMapping("")
    public String sendMailPage() {
        return "mail";
    }

    @PostMapping("/send")
    public String sendMail(@ModelAttribute Mail mail) throws IOException {
        sendMailService.sendMail(mail.getTo(), mail.getTitle(), mail.getContent());

        //TODO: 2023/11/14 메일 전송이 성공적으로 완료되는 경우와 실패하는 경우에 맞게 처리하기
        return "redirect:/";
    }
}
