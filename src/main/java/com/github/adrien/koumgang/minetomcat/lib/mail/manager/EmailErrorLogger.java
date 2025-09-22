package com.github.adrien.koumgang.minetomcat.lib.mail.manager;

import com.github.adrien.koumgang.minetomcat.apps.server.service.ServerEventLogService;
import com.github.adrien.koumgang.minetomcat.apps.server.view.ServerEventLogView;
import com.github.adrien.koumgang.minetomcat.lib.service.request.post.PostItemRequest;

import java.util.Date;

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

        PostItemRequest<ServerEventLogView> request = PostItemRequest.<ServerEventLogView>builder()
                .item(
                        new ServerEventLogView(
                                null,
                                "mail",
                                null,
                                error.getClass().getSimpleName(),
                                error.getMessage(),
                                null,
                                new Date(),
                                null
                        )
                )
                .build();
        ServerEventLogService.getInstance().postItem(request);

    }

}