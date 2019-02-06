package com.realtime.app.Model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Data
public class PlayerModel {
    private UUID id;
    private String userName;
    private String password;
    private int xPos;
    private int yPos;

    public void updatePosition(PlayerMovementModel positionUpdate){
        log.info(String.valueOf(positionUpdate));
        log.info(String.valueOf(xPos));
        log.info(String.valueOf(yPos));
        xPos += positionUpdate.getXMovement();
        yPos += positionUpdate.getYMovement();
    }

    public void setYposition(int pos) {
        yPos = pos;
    }

    public void setXposition(int pos) { xPos = pos;
    }
}