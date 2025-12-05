package com.smartshop.api.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
    public String hash(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
    public BCrypt.Result verify(String password, String hash) {
        return BCrypt.verifyer().verify(password.toCharArray(), hash);
    }
}
