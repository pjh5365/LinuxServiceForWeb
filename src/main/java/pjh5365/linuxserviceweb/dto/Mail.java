package pjh5365.linuxserviceweb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {
    private String to;
    private String title;
    private StringBuilder content;
}
