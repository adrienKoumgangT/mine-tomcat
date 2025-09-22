package com.github.adrien.koumgang.minetomcat.apps.account.dao;

import com.github.adrien.koumgang.minetomcat.apps.account.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDao {

    Optional<Account> findById(String id);

    List<Account> findAll();
    List<Account> findAll(int page, int pageSize);
    long count();

    List<Account> findAll(String idUser);
    List<Account> findAll(String idUser, int page, int pageSize);
    long count(String idUser);

    List<Account> findByArchived(String idUser, Boolean isArchived);
    List<Account> findByArchived(String idUser, Boolean isArchived, int page, int pageSize);
    long count(String idUser, Boolean isArchived);

    List<Account> findByName(String idUser, String name);
    List<Account> findByName(String idUser, String name, int page, int pageSize);
    long count(String idUser, String name);

    String save(Account account);

    boolean update(Account account);

    boolean delete(String id);

}
