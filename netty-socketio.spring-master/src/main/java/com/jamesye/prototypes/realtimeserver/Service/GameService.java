package com.jamesye.prototypes.realtimeserver.Service;

import com.jamesye.prototypes.realtimeserver.Model.PlayerModel;
import com.jamesye.prototypes.realtimeserver.Model.PlayerMovementModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class GameService {
    private Map<String, PlayerModel> playerList = new HashMap<String, PlayerModel>();

    public PlayerModel addPlayer(String userName) {
        PlayerModel newPlayer = new PlayerModel();

        HashMap<String, Integer> startingPosition = new HashMap<String, Integer>();
        startingPosition.put("x", 0);
        startingPosition.put("y", 0);

        newPlayer.setPosition(startingPosition);
        newPlayer.setUserName(userName);

        if (!playerList.containsKey(newPlayer.getUserName())){
            playerList.put(newPlayer.getUserName(), newPlayer);
            return newPlayer;
        }
        return null;
    }

    public boolean removePlayer(PlayerModel player) {
        if (!playerList.containsKey(player.getUserName())){
            playerList.remove(player);
            return true;
        }
        return false;
    }

    public Map updatePlayerPostion(PlayerMovementModel playerMovementModel){
        PlayerModel player = playerList.get(playerMovementModel.getUserName());
        player.updatePosition(playerMovementModel.getPlayerMovement());
        return playerList;
    };

    public Map getGameState(){
        return playerList;
    }
}
