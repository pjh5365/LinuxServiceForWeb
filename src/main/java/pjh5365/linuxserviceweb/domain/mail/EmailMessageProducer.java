package pjh5365.linuxserviceweb.domain.mail;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageProducer {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public EmailMessageProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendMessage(MailMessageDto messageDto) {
        amqpTemplate.convertAndSend("Email-auth", messageDto);
    }
}
