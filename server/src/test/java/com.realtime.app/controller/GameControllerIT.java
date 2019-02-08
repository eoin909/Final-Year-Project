package com.realtime.app.controller;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.protocol.Packet;
import com.realtime.app.Controller.GameController;
import com.realtime.app.Model.PlayerModel;
import com.realtime.app.Model.PlayerMovementModel;
import com.realtime.app.RealTimeServerApplication;
import com.realtime.app.Service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.SocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@Slf4j
@SpringBootTest(classes = RealTimeServerApplication.class)
@TestPropertySource(locations = "classpath:application-integration.properties")
@RunWith(SpringRunner.class)
public class GameControllerIT {
    @Autowired
    SocketIOServer socketIOServer;

    @Autowired
    private GameController gameController;

    @Test
    public void test() {
        //Todo: write tests for this
    }
}
