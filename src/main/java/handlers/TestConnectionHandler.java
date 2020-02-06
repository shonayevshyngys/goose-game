package handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import requestModels.BaseMessage;

public class TestConnectionHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.json(new BaseMessage("connected"));
        context.status(200);
    }
}
