package pjh5365.linuxserviceweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String getRoom(@PathVariable String roomId, Model model) {

        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());   // 로그인정보중 로그인명만 전달
        model.addAttribute("room", chatRoomRepository.findRoomById(roomId));
        return "room";  // 개인 채팅방 리턴하기
    }

    @PostMapping("/room")   // 채팅방 생성
    public String createRoom(String roomName) {
        String creator = SecurityContextHolder.getContext().getAuthentication().getName();
        chatRoomRepository.createChatRoomDto(roomName, creator);

        return "redirect:/chat/rooms";  // 채팅방 생성 후 채팅방 리스트로 이동
    }

    // DELETE 에 대한 처리가 로컬에서는 작동하지만 서버에서는 정상적으로 작동하지 않아 GET 방식으로 삭제하도록 수정함
    @GetMapping("/room-d/{roomId}")
    public String deleteRoom(@PathVariable String roomId) {
        chatRoomRepository.DeleteRoomDto(roomId);
        return "redirect:/chat/rooms";  // 채팅방 목록으로 리다이렉션
    }
}
