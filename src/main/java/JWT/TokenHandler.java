package JWT;

public class TokenHandler {
    public static final JWThandler jhandler;
    static {
        System.out.println("token handler initiated");
        jhandler = new JWThandler();
    }
}
