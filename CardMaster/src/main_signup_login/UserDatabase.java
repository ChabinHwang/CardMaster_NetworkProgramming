package main_signup_login;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static final Map<String, String> users = new HashMap<>();

    public static boolean addUser(String id, String password) {
        if (users.containsKey(id)) {
            return false;
        }
        users.put(id, password);
        return true;
    }

    public static boolean validateUser(String id, String password) {
        return users.containsKey(id) && users.get(id).equals(password);
    }
}