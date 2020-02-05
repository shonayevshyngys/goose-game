package utility;

import game.GooseGame;
import io.javalin.Javalin;

import java.util.ArrayList;

public class AppWrapper {
    private static Javalin app;
    private static ArrayList<GooseGame> games;

    static {
        app = Javalin.create().start(7000); //start the server
        games = new ArrayList<>();
    }

    public static Javalin getApp() {
        return app;
    }

    public static ArrayList<GooseGame> getGames() {
        return games;
    }

}
