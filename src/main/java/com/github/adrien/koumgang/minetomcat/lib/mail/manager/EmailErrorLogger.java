package com.github.adrien.koumgang.minetomcat.lib.mail.manager;

import com.github.adrien.koumgang.minetomcat.apps.server.service.ServerEventLogService;
import com.github.adrien.koumgang.minetomcat.apps.server.view.ServerEventLogView;

public class EmailErrorLogger {

    private static EmailErrorLogger instance;

    private EmailErrorLogger() {}

    public static EmailErrorLogger getInstance() {
        if(instance == null) {
            instance = new EmailErrorLogger();
        }
        return instance;
    }

    public void log(Throwable error){

        ServerEventLogView serverEventLogView = ServerEventLogService.getInstance()
                .saveServerEventLog(
                        "mail",
                        error.getClass().getSimpleName(),
                        error.getMessage(),
                        null,
                        null,
                        null,
                        null
                );

    }

}