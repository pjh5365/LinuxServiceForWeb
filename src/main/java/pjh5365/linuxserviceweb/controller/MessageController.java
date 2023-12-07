package pjh5365.linuxserviceweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pjh5365.linuxserviceweb.dto.ChatMessageDto;

@Controller
public class MessageController {

    private final SimpMessagingTemplate template;

    @Autowired
    public MessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    // 클라이언트가 전송하는 경로
    // config 에서 설정한 applicationDestinationPrefixes 와 @MessageMapping 경로가 병합
    // "/ws/chat/enter" 해당경로로 들어오면
    @MessageMapping("/chat/enter")
    public void enter(ChatMessageDto message) {
        message.setMessage(message.getSender() + " 님이 채팅방에 참여하였습니다.");
        // TODO: 2023/12/7 채팅 로그에 관한 코드는 여기에서 처리
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);  // 해당 경로로 메시지를 전송한다.
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessageDto message) {
        // TODO: 2023/12/7 채팅 로그에 관한 코드는 여기에서 처리
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
