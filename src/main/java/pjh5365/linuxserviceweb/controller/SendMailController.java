package pjh5365.linuxserviceweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pjh5365.linuxserviceweb.dto.MailDto;
import pjh5365.linuxserviceweb.service.SendMailService;

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
    public String sendMail(@ModelAttribute MailDto mailDto, ModelMap map) {
        sendMailService.sendMail(mailDto.getTo(), mailDto.getTitle(), mailDto.getContent());
        map.addAttribute("msg", "메일전송에 성공하였습니다. \n메인페이지로 이동합니다.");
        return "pageAction";
    }
}
