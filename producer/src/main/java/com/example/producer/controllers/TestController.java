package com.example.producer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class TestController {

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    public Map<String, Integer> messageCount = new HashMap<>();

    public static final String QUEUE_NAME = "messagesDestination";

    @GetMapping(value = "/sendMessages", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() {
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            Map<String, Object> headers = new HashMap<>();
            int groupId = random.nextInt(10000) + 1;
            headers.put("JMSXGroupID", groupId);
            String group = String.valueOf(groupId);
            Integer value = messageCount.get(group);
            if (value != null) {
                value += 1;
                messageCount.put(group, value);
            } else {
                messageCount.put(group, 1);
            }
            jmsMessagingTemplate.convertAndSend(QUEUE_NAME, "testMessage", headers);
        }
        System.out.println("CONVERTING MESSAGES");
        jmsMessagingTemplate.convertAndSend("consumer1", messageCount);
        jmsMessagingTemplate.convertAndSend("consumer2", messageCount);
        System.out.println("SENT GROUP INFO FROM PRODUCER " + messageCount.keySet().size());
        return "OK";
    }
}
