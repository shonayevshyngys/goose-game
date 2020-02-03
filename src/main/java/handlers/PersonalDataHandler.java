package handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import utility.UserData;

public class PersonalDataHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        UserData date = new UserData(context);
        context.json(date);
    }
}
