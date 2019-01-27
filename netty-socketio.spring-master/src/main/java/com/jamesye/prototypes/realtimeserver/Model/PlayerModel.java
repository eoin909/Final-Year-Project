package com.jamesye.prototypes.realtimeserver.Model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class PlayerModel {
    private UUID id;
    private String userName;
    private String password;
    private HashMap<String, Integer> position;

    public void updatePosition(Map<String, Integer> positionUpdate){
        Integer currentX = position.get("x") + positionUpdate.get("x");
        Integer currentY = position.get("y") + positionUpdate.get("y");

        position.put("x", currentX);
        position.put("y", currentY);
    }
}