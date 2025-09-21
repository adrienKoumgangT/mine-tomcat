package com.github.adrien.koumgang.minetomcat.lib.configuration.service;

import com.github.adrien.koumgang.minetomcat.lib.configuration.BaseConfiguration;
import com.github.adrien.koumgang.minetomcat.lib.configuration.annotation.ConfigFile;
import com.github.adrien.koumgang.minetomcat.lib.configuration.annotation.ConfigValue;

@ConfigFile(fileName = "config/mail.properties")
public class MailConfiguration extends BaseConfiguration {

    @ConfigValue(key = "smail.mtp.host")
    private String smtpHost;

    @ConfigValue(key = "mail.smtp.port")
    private String smtpPort;

    @ConfigValue(key = "mail.smtp.user")
    private String smtpUser;

    @ConfigValue(key = "mail.smtp.password")
    private String smtpPassword;

    @ConfigValue(key = "mail.smtp.address")
    private String smtpAddress;

    @ConfigValue(key = "mail.smtp.ssl.enable", defaultValue = "true")
    private boolean mailSmtpSslEnabled;

    public MailConfiguration() throws Exception {init();}

    public String getSmtpHost() {
        return smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public String getSmtpUser() {
        return smtpUser;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public boolean isMailSmtpSslEnabled() {
        return mailSmtpSslEnabled;
    }

    public boolean isSmtpAuth() {
        return smtpUser != null && !smtpUser.isBlank()
                && smtpPassword != null && !smtpPassword.isBlank()
                ;
    }

    public boolean isConfigured() {
        return isSmtpAuth()
                && smtpHost != null && !smtpHost.isBlank()
                && smtpPort != null && !smtpPort.isBlank()
                && smtpAddress != null && !smtpAddress.isBlank()
                ;
    }
}