package com.example.producer.jms;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsConsumer {

    private Map<String, Integer> groupInfo = new HashMap<>();
    private static final AtomicLong messageCount = new AtomicLong();

    @JmsListener(destination = "messagesDestination")
    public void onMessage(Message message) {
        try {
            final String group = message.getStringProperty("JMSXGroupID");
            final Integer count = (Integer) ((ObjectMessage) message).getObject();

            Integer value = groupInfo.compute(group, (k, v) -> count == 1 || v == null ? 1 : v + 1);

            if (!Objects.equals(value, count)) {
                System.out.println("Producer: Invalild group count for group " + group + ": expected=" + value + ", actual=" + count);
            }
            if (messageCount.incrementAndGet() % 100 == 0) {
                System.out.println(messageCount.get() + " messages received");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
