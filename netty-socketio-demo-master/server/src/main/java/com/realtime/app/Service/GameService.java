package com.realtime.app.Service;

import com.google.common.base.Strings;
import com.realtime.app.Model.PlayerModel;
import com.realtime.app.Model.PlayerMovementModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class GameService {

    public GameService() {
    }

    private Map<String, PlayerModel> playerList = new HashMap<>();

    public PlayerModel addPlayer(String userName) {
        if(!Strings.isNullOrEmpty(userName)) {
            PlayerModel newPlayer = new PlayerModel();

            newPlayer.setXposition(0);
            newPlayer.setYposition(0);
            newPlayer.setUserName(userName);
            newPlayer.setId(UUID.randomUUID());

            if (!playerList.containsKey(newPlayer.getUserName())) {
                playerList.put(newPlayer.getUserName(), newPlayer);
                return newPlayer;
            }
        }
        return null;
    }

    public Map<String, PlayerModel> removePlayer(String userName) {
        if (!Strings.isNullOrEmpty(userName) && playerList.containsKey(userName)){
            playerList.remove(userName);
        }
        return playerList;
    }

    public Map<String, PlayerModel> updatePlayerPostion(PlayerMovementModel playerMovementModel){
        if (playerMovementModel == null) return playerList;

        PlayerModel player = playerList.get(playerMovementModel.getUserName());
        player.updatePosition(playerMovementModel);
        return playerList;
    };

    public Map<String, PlayerModel> getGameState(){
        return playerList;
    }
}
