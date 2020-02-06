package game;

import io.javalin.websocket.WsContext;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GooseGame {
    private String name;
    private Map<WsContext, String> players;
    private ArrayList<String> playerList;
    private Map<String, Integer> positions;
    private boolean isStarted;
    private String host;
    private int nowTurn;

    public GooseGame(String name) {
        this.name = name;
        positions = new ConcurrentHashMap<>();
        players = new ConcurrentHashMap<>();
        isStarted = false;
        host = null;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ArrayList<String> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<String> playerList) {
        this.playerList = playerList;
    }

    public Map<String, Integer> getPositions() {
        return positions;
    }

    public void setPositions(Map<String, Integer> positions) {
        this.positions = positions;
    }

    public int getNowTurn() {
        return nowTurn;
    }

    public void setNowTurn(int nowTurn) {
        this.nowTurn = nowTurn;
    }

    public void playGame(){
        String playersNicknames = "";
        playerList = new ArrayList<>(players.values());
        for (String player: players.values()){
            playersNicknames = playersNicknames+" "+player;
            positions.put(player, 0);
        }
        broadcastToAllPlayers("Server", "Players:"+playersNicknames);
        broadcastToAllPlayers("Server", playerList.get(nowTurn)+" is turn to move");

    }

    private  void broadcastToAllPlayers(String sender, String message){
        players.keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
            session.send((sender+": "+message));
        });
    }
}
