package utility;

import JWT.TokenHandler;
import io.javalin.http.Context;

public class UserData {
    private String username;
    private String role;
    private String id;

    public UserData(Context ctx) {
        this.username = TokenHandler.jhandler.getPayload(ctx).getClaim("username").asString();
        this.role = TokenHandler.jhandler.getPayload(ctx).getClaim("role").asString();
        this.id = TokenHandler.jhandler.getPayload(ctx).getClaim("id").asString();
    }

    public UserData() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
