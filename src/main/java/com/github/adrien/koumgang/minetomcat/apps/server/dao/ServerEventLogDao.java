package com.github.adrien.koumgang.minetomcat.apps.server.dao;

import com.github.adrien.koumgang.minetomcat.apps.server.model.ServerEventLog;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface ServerEventLogDao {

    Optional<ServerEventLog> findById(String id);

    long count();
    List<ServerEventLog> findAll();
    List<ServerEventLog> findAll(int page, int pageSize);

    List<String> listDistinctEvents();

    long countByEvent(String event);
    List<ServerEventLog> findByEvent(String event);
    List<ServerEventLog> findByEvent(String event, int page, int pageSize);

    long countByName(String event);
    List<ServerEventLog> findByName(String name);
    List<ServerEventLog> findByName(String name, int page, int pageSize);

    long countByEventAndName(String event, String name);
    List<ServerEventLog> findByEventAndName(String event, String name);
    List<ServerEventLog> findByEventAndName(String event, String name, int page, int pageSize);

    String save(ServerEventLog serverEventLog);

    boolean delete(String id);

}
