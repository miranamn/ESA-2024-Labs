package com.example.book.receivers;

import com.example.book.models.ChangeLog;
import com.example.book.models.EmailMessage;
import com.example.book.repositories.EmailMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageReceiver {
    @Autowired
    private EmailMessageRepository emailMessageRepository;

    @JmsListener(destination = "event", containerFactory = "myFactory")
    public void receiveAndProcessEvent(ChangeLog event) {
        String message = String.format("Action performed: %s on entity type: %s",
                event.getChangeType(), event.getEntity());

        EmailMessage email = new EmailMessage();
        email.setAddress("admin");
        email.setContent(message);

        emailMessageRepository.save(email);
    }
}
