package com.realtime.app.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class PlayerMovementModel {
    private String userName;
    @JsonProperty("xMovement")
    private int xMovement;
    @JsonProperty("yMovement")
    private int yMovement;
}