package com.example.producer.to;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GroupInfo implements Serializable {

    Map<Integer, Integer> groupInfo = new HashMap<>();

    public GroupInfo(Map<Integer, Integer> groupInfo) {
        this.groupInfo = groupInfo;
    }
}
