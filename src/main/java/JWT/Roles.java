package JWT;

import io.javalin.core.security.Role;

public enum Roles implements Role {
    ANYONE,
    USER,
    ADMIN
}
