package handlers;

import JWT.Roles;
import game.GooseGame;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import requestModels.BaseMessage;
import requestModels.GameInfo;
import utility.AppWrapper;
import utility.UserData;

import java.util.Collections;

public class CreateGameHandler implements Handler {
    GooseGame game;
    @Override
    public void handle(@NotNull Context context) throws Exception {
        UserData data = new UserData(context);
        GameInfo info = context.bodyAsClass(GameInfo.class);
        game = new GooseGame(info.getInfo());
        System.out.println("sending the code 200");
        context.status(200);
        context.json(new BaseMessage("game created"));
        AppWrapper.getGames().add(game);
        AppWrapper.getApp().ws("game_"+info, ws -> {
            ws.onConnect(ctx -> {
                ctx.send("Welcome to lobby");
                System.out.println("somebody connected to game "+ info);
            });
            ws.onMessage(ctx -> {
                String message = ctx.message();
                if (message.contains("registration") && !game.isStarted()){
                    String[] tokens = message.split(" ");
                    game.getPlayers().put(ctx, tokens[1]);
                    broadcastToAllPlayers("Server", "Player "+tokens[1]+" connected to game");
                }
            });
        }, Collections.singleton(Roles.ANYONE));
    }

    private  void broadcastToAllPlayers(String sender, String message){
        game.getPlayers().keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
            session.send((sender+": "+message));
        });
    }
}
