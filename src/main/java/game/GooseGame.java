package game;

import io.javalin.websocket.WsContext;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GooseGame {
    private String name;
    private Map<WsContext, String> players;
    private boolean isStarted;

    public GooseGame(String name) {
        this.name = name;
        players = new ConcurrentHashMap<>();
        isStarted = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<WsContext, String> getPlayers() {
        return players;
    }

    public void setPlayers(Map<WsContext, String> players) {
        this.players = players;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
