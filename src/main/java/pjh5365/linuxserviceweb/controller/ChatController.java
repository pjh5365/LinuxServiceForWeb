package pjh5365.linuxserviceweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pjh5365.linuxserviceweb.repository.ChatRoomRepository;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatController(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @GetMapping("/rooms")   // 전체 채팅방가져오기
    public String rooms(Model model) {
        model.addAttribute("list", chatRoomRepository.findAllRooms());

        return "room-list";
    }

    @GetMapping("/room/{roomId}")
    public String getRoom(@PathVariable String roomId, Model model, @AuthenticationPrincipal UserDetails userDetails) {

        model.addAttribute("room", chatRoomRepository.findRoomById(roomId));
        model.addAttribute("userDetails", userDetails);  // 시큐리티에서 사용자 정보받아 타임리프로 넘기기
        return "room";  // 개인 채팅방 리턴하기
    }

    @PostMapping("/room/{roomName}")   // 채팅방 생성
    public String createRoom(@PathVariable String roomName) {
        chatRoomRepository.createChatRoomDto(roomName);

        return "redirect:/chat/rooms";  // 채팅방 생성 후 채팅방 리스트로 이동
    }
}
