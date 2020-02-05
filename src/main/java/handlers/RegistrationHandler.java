package handlers;

import domain.dao.UserDAO;
import domain.model.User;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import requestModels.BaseMessage;
import requestModels.Credentials;

import java.util.Date;

public class RegistrationHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        Credentials cr = context.bodyAsClass(Credentials.class);
        if (UserDAO.getByUsername(cr.getUsername())==null){
            User user = new User(cr.getUsername(), cr.getPassword());
            UserDAO.persist(user);
            context.json(new BaseMessage("Created user"));
            context.status(201);
        }
        else {
            context.json(new BaseMessage("User already exists"));
            context.status(400);
        }

    }
}
