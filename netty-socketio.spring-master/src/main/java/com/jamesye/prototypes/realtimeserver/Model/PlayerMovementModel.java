package com.jamesye.prototypes.realtimeserver.Model;

import lombok.Data;

import java.util.Map;

@Data
public class PlayerMovementModel {
    private String userName;
    private Map<String, Integer> playerMovement;
}