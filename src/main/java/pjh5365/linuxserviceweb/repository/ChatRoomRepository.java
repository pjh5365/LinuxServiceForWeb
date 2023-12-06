package pjh5365.linuxserviceweb.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import pjh5365.linuxserviceweb.dto.ChatRoomDto;

import java.util.*;

@Repository
public class ChatRoomRepository {
    private Map<String, ChatRoomDto> map;

    @PostConstruct  // 빈이 최초 한번만 초기화하는것을 보장
    public void init() {
        map = new LinkedHashMap<>();
    }

    public List<ChatRoomDto> findAllRooms() {   // 모든 채팅방 가져오기
        List<ChatRoomDto> list = new ArrayList<>(map.values());
        Collections.reverse(list);

        return list;
    }

    public ChatRoomDto findRoomById(String roomId) {    // id 로 채팅방 찾기
        return map.get(roomId);
    }

    public void createChatRoomDto(String roomName, String creator) { // 채팅방 생성
        ChatRoomDto room = ChatRoomDto.createRoom(roomName, creator);
        map.put(room.getRoomId(), room);
    }

    public void DeleteRoomDto(String roomId) {  // 채팅방 삭제하기
        map.remove(roomId); // 자바에서는 직접 할당해제를 하지않고 해당 레퍼런스를 아무 객체도 참조하지 않는다면 가비지컬렉터가 회수함
    }
}
