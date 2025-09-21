package com.github.adrien.koumgang.minetomcat.lib.configuration.service;

import com.github.adrien.koumgang.minetomcat.lib.configuration.BaseConfiguration;
import com.github.adrien.koumgang.minetomcat.lib.configuration.annotation.ConfigFile;
import com.github.adrien.koumgang.minetomcat.lib.configuration.annotation.ConfigValue;

@ConfigFile(fileName = "config/swagger.properties")
public class SwaggerConfiguration extends BaseConfiguration {

    @ConfigValue(key = "swagger.security.enable", defaultValue = "true")
    private boolean securityEnable;

    @ConfigValue(key = "swagger.security.ip.list", defaultValue = "localhost,0:0:0:0:0:0:0:1,127.0.0.1")
    private String securityIpList;

    public SwaggerConfiguration() throws Exception {init();}

    public boolean isSecurityEnable() {
        return securityEnable;
    }

    public String getSecurityIpList() {
        return securityIpList;
    }
}
