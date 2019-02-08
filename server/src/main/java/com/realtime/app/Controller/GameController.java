package com.realtime.app.Controller;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.realtime.app.Service.GameService;
import com.corundumstudio.socketio.listener.DataListener;
import com.realtime.app.Model.PlayerModel;
import com.realtime.app.Model.PlayerMovementModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GameController {

        @Autowired
        private GameService gameService;
        private final SocketIONamespace namespace;

        @Autowired
        public GameController(SocketIOServer server) {
        this.namespace = server.addNamespace("/game");
        this.namespace.addEventListener("playerJoined", String.class, processPlayerJoined());
        this.namespace.addEventListener("updatePlayerPosition", PlayerMovementModel.class, processsPlayerMovement());
        this.namespace.addEventListener("playerLeft", String.class, processsPlayerLeft());
        }

    private DataListener<String> processPlayerJoined() {
            return (client, userName, ackSender) -> {
                PlayerModel player = gameService.addPlayer(userName);
                if(player != null) {
                    client.sendEvent("setPlayer", player);
                }
                namespace.getBroadcastOperations().sendEvent("mapUpdate", gameService.getGameState().values().stream().collect(Collectors.toList()));
            };
        };

        private DataListener<PlayerMovementModel> processsPlayerMovement() {
            return (client, data, ackSender) -> {
                List<PlayerModel> playerList = gameService.updatePlayerPostion(data).values().stream().collect(Collectors.toList());
                namespace.getBroadcastOperations().sendEvent("mapUpdate", playerList);
            };
        };

    private DataListener<String> processsPlayerLeft() {
        return (client, userName, ackRequest) -> {
            List<PlayerModel> playerList = gameService.removePlayer(userName).values().stream().collect(Collectors.toList());
            namespace.getBroadcastOperations().sendEvent("mapUpdate", playerList);
        };
    };
}
