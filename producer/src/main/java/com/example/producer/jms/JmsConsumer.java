package com.example.producer.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.HashMap;
import java.util.Map;

@Component
public class JmsConsumer {

    public Map<String, Integer> groupInfo = new HashMap<>();

    @JmsListener(destination = "messagesDestination")
    public void onMessage(Message message) {
        try {
            Integer value = groupInfo.get(message.getStringProperty("JMSXGroupID"));
            if (value != null) {
                value += 1;
                groupInfo.put(message.getStringProperty("JMSXGroupID"), value);
            } else {
                groupInfo.put(message.getStringProperty("JMSXGroupID"), 1);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @JmsListener(destination = "consumer1")
    public void checkGroupInfo(Map<String, Integer> producerGroupInfo) {
        System.out.println("RECEIVED GROUP INFO FROM PRODUCER: " + producerGroupInfo.keySet().size());
        System.out.println("GROUP INFO FROM CONSUMER: " + groupInfo.keySet().size());
        boolean hasErrors = false;
        try {
            Map<String, Integer> extractedGroupInfo = new HashMap<>();
            for (String groupId : groupInfo.keySet()) {
                extractedGroupInfo.put(groupId, producerGroupInfo.get(groupId));
            }
            for (Map.Entry entry : groupInfo.entrySet()) {
                Integer numberOfMessages = extractedGroupInfo.get(entry.getKey());
                if (numberOfMessages != entry.getValue()) {
                    System.out.println("BAD GROUP INFO FOR GROUPID: " + entry.getKey() + ". Expected number of messages is: " + entry.getValue()
                            + ", but actual number was: " + numberOfMessages);
                    hasErrors = true;
                }
            }
            if (hasErrors) {
                System.out.println("There were errors with receiving messages");
                return;
            }
            System.out.println("All messages received successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
