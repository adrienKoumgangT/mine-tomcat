package com.github.adrien.koumgang.minetomcat.lib.controller;

import com.github.adrien.koumgang.minetomcat.lib.authentication.token.TokenManager;
import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.exception.safe.InvalidAuthentificationException;
import com.github.adrien.koumgang.minetomcat.lib.exception.safe.InvalidTokenException;

public class BaseController {

    public static UserToken getUserToken(String token) throws Exception {
        if(token == null) throw new InvalidTokenException("token is null");

        try  {
            return TokenManager.readToken(token.replace("Bearer ", ""));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidAuthentificationException("Invalid token");
        }
    }

}
