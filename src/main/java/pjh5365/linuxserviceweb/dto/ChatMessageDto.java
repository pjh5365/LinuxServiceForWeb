package pjh5365.linuxserviceweb.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessageDto {
    private String roomId;
    private String sender;
    private String message;
}
