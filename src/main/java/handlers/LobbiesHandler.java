package handlers;

import game.GooseGame;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utility.AppWrapper;

import java.util.ArrayList;

public class LobbiesHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        ArrayList<GooseGame> games = AppWrapper.getGames();
        ArrayList<String> names = new ArrayList<>();
        games.forEach(game -> {
            if (!game.isStarted()){
                names.add(game.getName());
            }
        });
        context.json(names);
        context.status(200);
    }
}
