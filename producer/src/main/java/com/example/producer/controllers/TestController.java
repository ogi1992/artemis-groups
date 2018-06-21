package com.example.producer.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;


    public static final String QUEUE_NAME = "messagesDestination";

    @GetMapping(value = "/sendMessages/{nrOfGroups}/{nrOfMessages}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test(@PathVariable int nrOfGroups, @PathVariable int nrOfMessages) {
        final Random random = new Random();
        final Map<Integer, Integer> messageCount = new HashMap<>();
        long start = System.currentTimeMillis();

        for (int i = 0; i < nrOfMessages; i++) {
            int groupId = random.nextInt(nrOfGroups) + 1;

            final Integer count = messageCount.compute(groupId, (k, v) -> v == null ? 1 : v + 1);

            final Map<String, Object> headers = new HashMap<>();
            headers.put("JMSXGroupID", groupId);

            jmsMessagingTemplate.convertAndSend(QUEUE_NAME, count, headers);

            if ((i + 1) % 1000 == 0) {
                long stop = System.currentTimeMillis();
                System.out.println("Sent " + (i + 1) + " messages in " + (stop - start) + "ms");
            }

        }

        return "OK";
    }
}
