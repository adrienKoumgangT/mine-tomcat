package com.github.adrien.koumgang.minetomcat.apps.test.dao;

import com.github.adrien.koumgang.minetomcat.apps.test.model.Test;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface TestDao {

    Optional<Test> findById(String id);

    long count();
    List<Test> findAll();
    List<String> findAllIds();
    List<Test> findAll(int page, int pageSize);
    List<String> findAllIds(int page, int pageSize);

    long countByName(String name);
    List<Test> findByName(String name);
    List<Test> findByName(String name, int page, int pageSize);
    List<String> findIdsByName(String name);
    List<String> findIdsByName(String name, int page, int pageSize);

    String save(Test test);

    boolean update(Test test);

    boolean delete(String id);

}