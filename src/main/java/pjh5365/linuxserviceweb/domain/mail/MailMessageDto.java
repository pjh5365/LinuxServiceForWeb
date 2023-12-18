package pjh5365.linuxserviceweb.domain.mail;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MailMessageDto implements Serializable {

    private String path;
    private String to;
    private String subject;
    private String content;

}
