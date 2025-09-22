package com.github.adrien.koumgang.minetomcat.apps.metadata.dao;

import com.github.adrien.koumgang.minetomcat.apps.metadata.model.Metadata;

import java.util.List;
import java.util.Optional;

public interface MetadataDao {

    Optional<Metadata> findById(String id);

    List<Metadata> findByType();
    List<Metadata> findByType(int page, int pageSize);
    long count();

    List<Metadata> findByType(String idUser);
    List<Metadata> findByType(String idUser, int page, int pageSize);
    long count(String idUser);

    List<Metadata> findByType(String idUser, String metadataType);
    List<Metadata> findByType(String idUser, String metadataType, int page, int pageSize);
    long count(String idUser, String metadataType);

    List<Metadata> findByName(String idUser, String metadataType, String name);
    List<Metadata> findByName(String idUser, String metadataType, String name, int page, int pageSize);
    long count(String idUser, String metadataType, String name);

    String save(Metadata metadata);
    boolean update(Metadata metadata);
    boolean delete(String id);

}
