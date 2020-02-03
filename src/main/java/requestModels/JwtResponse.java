package requestModels;

public class JwtResponse {
    private String type;
    private String token;

    public JwtResponse(String token){
        this.type = "Bearer";
        this.token = token;
    }

    public JwtResponse() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
