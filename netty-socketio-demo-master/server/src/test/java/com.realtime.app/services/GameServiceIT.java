package com.realtime.app.services;

import com.realtime.app.Model.PlayerModel;
import com.realtime.app.Model.PlayerMovementModel;
import com.realtime.app.RealTimeServerApplication;
import com.realtime.app.Service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RealTimeServerApplication.class)
@TestPropertySource(locations = "classpath:application-integration.properties")
public class GameServiceIT {
    @Autowired
    private GameService gameService;
    private static String username = "username";

    //add method
    @Test
    public void testAddPlayer() {
        PlayerModel playerModel = gameService.addPlayer(username);
        assertEquals(username, playerModel.getUserName());
        assertNull(playerModel.getPassword());
        assertNotNull(playerModel.getId());
        assertNotNull(playerModel.getXPos());
        assertNotNull(playerModel.getYPos());
        assertTrue(gameService.getGameState().containsKey(username));
    }

    @Test
    public void testAddPlayerDup() {
        String newUserName = "thisUser";
        assertNotNull(gameService.addPlayer(newUserName));
        assertTrue(gameService.getGameState().containsKey(newUserName));
        assertNull(gameService.addPlayer(newUserName));
        assertTrue(gameService.getGameState().containsKey(newUserName));
    }

    @Test
    public void testAddPlayerNullUsername() {
        assertNull(gameService.addPlayer(null));
        assertFalse(gameService.getGameState().containsKey(null));
    }

    @Test
    public void testAddPlayerEmptyString() {
        assertNull(gameService.addPlayer(""));
        assertFalse(gameService.getGameState().containsKey(""));
    }

    //remove method
    @Test
    public void testRemovePlayer() {
        gameService.addPlayer(username);
        assertTrue(gameService.getGameState().containsKey(username));

        gameService.removePlayer(username);
        assertFalse(gameService.getGameState().containsKey(username));
    }

    @Test
    public void testRemovePlayerNotExisted() {
        gameService.removePlayer(username);
        assertFalse(gameService.getGameState().containsKey(username));
    }

    @Test
    public void testRemovePlayerNullUsername() {
        assertNotNull(gameService.removePlayer(null));
    }

    @Test
    public void testRemovePlayerEmptyString() {
        assertNotNull(gameService.removePlayer(""));
    }

    @Test
    public void testUpdatePlayerPosition() {
        PlayerModel playerModel = gameService.addPlayer(username);
        int xPos = playerModel.getXPos();
        int yPos = playerModel.getXPos();

        PlayerMovementModel playerMovementModel = new PlayerMovementModel();
        playerMovementModel.setUserName(username);
        playerMovementModel.setXMovement(1);
        playerMovementModel.setYMovement(0);

        PlayerModel currentPlayerModel = gameService.updatePlayerPostion(playerMovementModel).get(username);
        assertEquals(1, (currentPlayerModel.getXPos() - xPos));
        assertEquals(currentPlayerModel.getYPos(), yPos);
    }

    @Test
    public void testUpdatePlayerPositionNull() {
        String playerUsername = "testUpdatePlayerPositionNull";
        PlayerModel playerModel = gameService.addPlayer(playerUsername);
        int xPos = playerModel.getXPos();
        int yPos = playerModel.getYPos();

        PlayerModel currentPlayerModel = gameService.updatePlayerPostion(null).get(playerUsername);
        log.info(String.valueOf(currentPlayerModel));
        assertEquals(currentPlayerModel.getXPos(), xPos);
        assertEquals(currentPlayerModel.getYPos(), yPos);
    }

    @Test
    public void testGetGameState() {
        gameService.addPlayer("player1");
        gameService.addPlayer("player2");
        gameService.addPlayer("player3");

        Map<String, PlayerModel> playerModelMap = gameService.getGameState();

        assertTrue(playerModelMap.containsKey("player1"));
        assertTrue(playerModelMap.containsKey("player2"));
        assertTrue(playerModelMap.containsKey("player3"));
    }
}
