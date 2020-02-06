package handlers;

import JWT.Roles;
import domain.dao.GameDAO;
import domain.model.Game;
import game.GooseGame;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import requestModels.BaseMessage;
import requestModels.GameInfo;
import utility.AppWrapper;
import utility.UserData;

import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class CreateGameHandler implements Handler {
    GooseGame game;
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Random r = new Random();
        UserData data = new UserData(context);
        GameInfo info = context.bodyAsClass(GameInfo.class);
        boolean gameNameExists = false;
        if (AppWrapper.getGames().size() == 0){
            gameNameExists = false;
        }
        else {
           for (GooseGame line : AppWrapper.getGames()){
               if (line.getName().equalsIgnoreCase(info.getInfo())){
                   gameNameExists = true;
                   break;
               }
           }
        }
        if (!gameNameExists){
            game = new GooseGame(info.getInfo());
            context.status(200);
            context.json(new BaseMessage("game created"));
            Game transientGame = new Game();
            transientGame.setCreated_at(new Date());
            AppWrapper.getGames().add(game);
            AppWrapper.getApp().ws("/game_"+info.getInfo(), ws -> {
                ws.onConnect(ctx -> {
                    ctx.send("Server: Welcome to lobby");
                    System.out.println("somebody connected to game "+ info.getInfo());
                });
                ws.onMessage(ctx -> {
                    String message = ctx.message();
                    String[] tokens = message.split(" ");
                    if (message.contains("/registration") && !game.isStarted()){

                        game.getPlayers().put(ctx, tokens[1]);
                        broadcastToAllPlayers("Server", "Player "+tokens[1]+" connected to game");
                        if (game.getPlayers().size() == 1){
                            game.setHost(tokens[1]);
                        }
                    }
                    if (message.contains("/showplayers")){
                        game.getPlayers().values().forEach(ctx::send);
                    }
                    if (message.contains("/chat"))
                    {
                        String chatMessage = message.substring(6);
                        broadcastToAllPlayers(game.getPlayers().get(ctx), chatMessage);
                    }
                    if (message.contains("/start") && !game.isStarted()){
                        String sendername = game.getPlayers().get(ctx);
                        String gameHost = game.getHost();
                        if (sendername.equalsIgnoreCase(gameHost)){
                            if (game.getPlayers().size() == 1){
                                ctx.send("Server: only one player, unable to start");
                            }
                            else{
                                game.setStarted(true);
                                broadcastToAllPlayers("Server","Game is starting");
                                game.playGame();
                            }

                        }
                        else{
                            ctx.send("Server: Only host can start a game");
                        }
                    }

                    if(message.equalsIgnoreCase("/roll") && game.isStarted()){
                        String nowTurnPlayer = game.getPlayerList().get(game.getNowTurn());
                        if (game.getPlayers().get(ctx).equals(nowTurnPlayer)){
                            int die1 = r.nextInt(6)+1;
                            int die2 = r.nextInt(6)+1;
                            int initialPosition = game.getPositions().get(nowTurnPlayer);
                            int finishedPosition = initialPosition+die1+die2;
                            game.getPositions().put(nowTurnPlayer, finishedPosition);

                            broadcastToAllPlayers("Server", "Player "+nowTurnPlayer+" rolled "+die1+" "+die2+" and " +
                                    "moved from position " + initialPosition + " to position "+finishedPosition);
                            if (finishedPosition > 63){
                                broadcastToAllPlayers("Server", nowTurnPlayer+" won");
                                transientGame.setFinished_at(new Date());
                                transientGame.setUsername(nowTurnPlayer);
                                GameDAO.persist(transientGame);
                                closeAllConnections();
                            }
                            else{
                                if (game.getNowTurn() == game.getPlayerList().size()-1){
                                    if (finishedPosition == 6){
                                        broadcastToAllPlayers("Server", "Player "+ nowTurnPlayer +" got on Bridge and moves to 12");
                                        game.getPositions().put(nowTurnPlayer, 12);
                                    }
                                    if (finishedPosition == 5 || finishedPosition == 9 || finishedPosition == 14 || finishedPosition == 18 || finishedPosition == 23 ||
                                            finishedPosition == 27){
                                        broadcastToAllPlayers("Server", "Player "+ nowTurnPlayer +" got on Goose and can move again");
                                    }
                                    else{
                                        game.setNowTurn(0);
                                    }
                                }
                                else{
                                    if (finishedPosition == 6){
                                        broadcastToAllPlayers("Server", "Player "+ nowTurnPlayer +" got on Bridge and moves to 12");
                                        game.getPositions().put(nowTurnPlayer, 12);
                                    }
                                    if (finishedPosition == 5 || finishedPosition == 9 || finishedPosition == 14 || finishedPosition == 18 || finishedPosition == 23 ||
                                            finishedPosition == 27){
                                        broadcastToAllPlayers("Server", "Player "+ nowTurnPlayer +" got on Goose and can move again");
                                    }
                                    else{
                                        game.setNowTurn(game.getNowTurn()+1);
                                    }

                                }
                                broadcastToAllPlayers("Server", game.getPlayerList().get(game.getNowTurn())+" is turn to move");
                            }

                        }
                        else {
                            ctx.send("Server: It's not your turn");
                        }

                    }
                    System.out.println(message);
                });
                ws.onClose(ctx -> {
                    String username = game.getPlayers().get(ctx);
                    game.getPlayers().remove(ctx);
                    broadcastToAllPlayers(username, "left the game");
                    if(game.getPlayers().size() == 0){
                        AppWrapper.getGames().remove(game);
                    }
                });
            }, Collections.singleton(Roles.ANYONE));
        }
        else {
            context.status(400);
        }

    }

    private  void broadcastToAllPlayers(String sender, String message){
        game.getPlayers().keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
            session.send((sender+": "+message));
        });
    }

    private void closeAllConnections(){
        game.getPlayers().keySet().forEach(ctx -> ctx.session.close());
    }
}
