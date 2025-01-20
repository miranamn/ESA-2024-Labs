package com.example.book.config;

import com.example.book.models.ChangeLog;
import jakarta.annotation.PostConstruct;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import jakarta.jms.Topic;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DetectingChanges {
    private final JmsTemplate jmsTemplate;
    private Topic topic;

    @Autowired
    public DetectingChanges(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostConstruct
    private void initTopic() throws JMSException {
        this.topic = jmsTemplate.getConnectionFactory()
                .createConnection()
                .createSession(false, Session.AUTO_ACKNOWLEDGE)
                .createTopic("event");
    }

    @AfterReturning(
            value = "within(com.example.book.services.*) && @annotation(com.example.book.config.ChangeLogging)",
            returning = "returnValue"
    )
    public void performLogging(JoinPoint joinPoint, Object returnValue) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String entity = extractEntityName(className);
        String changeType = determineEventType(methodName);

        if (!changeType.isEmpty()) {
            ChangeLog logging = new ChangeLog();
            logging.setEntity(entity);
            logging.setChangeType(changeType);
            jmsTemplate.convertAndSend(topic, logging);
        }
    }

    private String extractEntityName(String className) {
        return className.replace("Service", "");
    }

    private String determineEventType(String methodName) {
        return switch (methodName) {
            case "create" -> "CREATE";
            case "update" -> "UPDATE";
            case "delete" -> "DELETE";
            default -> "";
        };
    }
}
