package handlers;

import JWT.TokenHandler;
import domain.dao.UserDAO;
import domain.model.User;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import requestModels.Credentials;
import requestModels.JwtResponse;
import utility.HashUtils;

import java.util.Date;

public class LoginHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Credentials cr = context.bodyAsClass(Credentials.class);
        //check
        User u = UserDAO.getByUsername(cr.getUsername());
        if (u != null && HashUtils.checkPassword(cr.getPassword(), u.getPassword())) {
            context.status(200);
            String token = TokenHandler.jhandler.getToken(u);
            context.json(new JwtResponse(token));
        } else {
            context.status(401);
            context.result("Bad Credentials");
        }


    }
}
