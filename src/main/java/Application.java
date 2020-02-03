import JWT.JavalinJWT;
import JWT.Roles;
import domain.HibernateUtils;
import handlers.LoginHandler;
import handlers.PersonalDataHandler;
import handlers.RegistrationHandler;
import io.javalin.Javalin;
import JWT.TokenHandler;
import io.javalin.http.Handler;

import java.util.Collections;

public class Application {
    public static void main(String[] args){
        Javalin app = Javalin.create().start(7000);
        HibernateUtils.getSession();
        app.config.accessManager(TokenHandler.jhandler.getAccessManager());
        Handler decodeHandler = JavalinJWT.createHeaderDecodeHandler(TokenHandler.jhandler.getProvider());
        app.before(decodeHandler);
        app.get("/", ctx -> ctx.result("Hello world"));
        app.post("/signup", new RegistrationHandler(), Collections.singleton(Roles.ANYONE));
        app.post("/login", new LoginHandler(),Collections.singleton(Roles.ANYONE));
        app.get("/userdata", new PersonalDataHandler(), Collections.singleton(Roles.USER));
    }
}
