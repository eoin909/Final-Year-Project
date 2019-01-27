package com.jamesye.prototypes.realtimeserver.Controller;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.jamesye.prototypes.realtimeserver.Model.PlayerModel;
import com.jamesye.prototypes.realtimeserver.Model.PlayerMovementModel;
import com.jamesye.prototypes.realtimeserver.Service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GameController {

    @Autowired
    private GameService gameService;
    private final SocketIONamespace namespace;

    @Autowired
    public GameController(SocketIOServer server) {
        this.namespace = server.addNamespace("/game");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("playerMovement", PlayerMovementModel.class, onPlayerMovement());
    }

    private DataListener<PlayerMovementModel> onPlayerMovement() {

        return (client, data, ackSender) -> {
            log.info("Client[{}] - Player Movement '{}'", client.getSessionId().toString(), data);
            namespace.getBroadcastOperations().sendEvent("mapUpdate", gameService.updatePlayerPostion(data));
        };
    }

    private ConnectListener onConnected() {
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            PlayerModel player = gameService.addPlayer("player" + Math.abs(Math.random() * 1000) );

            log.info("Client[{}] - Connected to game module through '{}'", client.getSessionId().toString(), handshakeData.getUrl(), player);

            client.sendEvent("playerAdded", player);
            namespace.getBroadcastOperations().sendEvent("playerListUpated", gameService.getGameState());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from game module.", client.getSessionId().toString());
        };
    }
}
