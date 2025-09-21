package com.github.adrien.koumgang.minetomcat.lib.configuration.service;


import com.github.adrien.koumgang.minetomcat.lib.configuration.BaseConfiguration;
import com.github.adrien.koumgang.minetomcat.lib.configuration.annotation.ConfigFile;
import com.github.adrien.koumgang.minetomcat.lib.configuration.annotation.ConfigValue;

@ConfigFile(fileName = "config/Configuration.properties")
public class ApiConfiguration extends BaseConfiguration {

    @ConfigValue(key = "prod", defaultValue = "false")
    private boolean prod;

    @ConfigValue(key = "debug", defaultValue = "true")
    private boolean debug;

    @ConfigValue(key = "base.api.url", defaultValue = "http://localhost:8080")
    private String baseApiUrl;

    @ConfigValue(key = "jwts.private.key")
    private String jwsPrivateKey;

    @ConfigValue(key = "jwts.public.key")
    private String jwsPublicKey;

    @ConfigValue(key = "jwts.key")
    private String jwsKey;


    public ApiConfiguration() throws Exception {init();}

    public boolean isProd() {
        return prod;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getBaseApiUrl() {
        return baseApiUrl;
    }

    public String getJwsPrivateKey() {
        return jwsPrivateKey;
    }

    public String getJwsPublicKey() {
        return jwsPublicKey;
    }

    public String getJwsKey() {
        return jwsKey;
    }
}
