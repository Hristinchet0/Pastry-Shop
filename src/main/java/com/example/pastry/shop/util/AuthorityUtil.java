package com.example.pastry.shop.util;

import com.example.pastry.shop.model.entity.Users;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class AuthorityUtil {

    public static Boolean hasRole(String role, Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> auth.getAuthority().equals(role));
    }
}
