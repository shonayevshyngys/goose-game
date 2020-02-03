package utility;

import org.mindrot.jbcrypt.BCrypt;

public class HashUtils {
    public static String hashPassword(String password){
        String hashed = BCrypt.hashpw(password,BCrypt.gensalt());
        return hashed;
    }

    public static boolean checkPassword(String candidate, String hashed){
        return (BCrypt.checkpw(candidate,hashed));
    }
}
