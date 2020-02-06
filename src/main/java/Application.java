import JWT.JavalinJWT;
import JWT.Roles;
import domain.HibernateUtils;
import handlers.*;
import JWT.TokenHandler;
import io.javalin.http.Handler;
import utility.AppWrapper;

import java.io.IOException;
import java.util.Collections;

public class Application {

    public static void main(String[] args) throws IOException {

        /*
        Server start in the AppWrapper class in utility package
        This allows server to dynamically create sessions
         */
        HibernateUtils.getSession(); // initialize the Hibernate

        AppWrapper.getApp().config.accessManager(TokenHandler.jhandler.getAccessManager());
        Handler decodeHandler = JavalinJWT.createHeaderDecodeHandler(TokenHandler.jhandler.getProvider());
        AppWrapper.getApp().before(decodeHandler);
        // initialize the Session control

        //static routes
        AppWrapper.getApp().post("/signup", new RegistrationHandler(), Collections.singleton(Roles.ANYONE));
        AppWrapper.getApp().post("/login", new LoginHandler(),Collections.singleton(Roles.ANYONE));
        AppWrapper.getApp().get("/userdata", new PersonalDataHandler(), Collections.singleton(Roles.USER));
        AppWrapper.getApp().get("/getlobbies", new LobbiesHandler(), Collections.singleton(Roles.USER));
        AppWrapper.getApp().post("/creategame", new CreateGameHandler(), Collections.singleton(Roles.USER) );
        AppWrapper.getApp().get("/testconnection", new TestConnectionHandler(), Collections.singleton(Roles.ANYONE));

        AppWrapper.getApp().ws("/chat", ws -> {
            ws.onConnect(ctx -> {
                System.out.println("connected");
            });
            ws.onMessage(ctx -> {
                System.out.println(ctx.message());
                ctx.send("this is server");
            });
        }, Collections.singleton(Roles.ANYONE));
    }
}
