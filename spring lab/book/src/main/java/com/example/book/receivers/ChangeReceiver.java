package com.example.book.receivers;

import com.example.book.models.ChangeLog;
import com.example.book.repositories.ChangeLogRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class ChangeReceiver {
    @Autowired
    private ChangeLogRepository logRepository;

    @JmsListener(destination = "event", containerFactory = "myFactory")
    public void receive(ChangeLog log) {
        logRepository.save(log);
    }
}
