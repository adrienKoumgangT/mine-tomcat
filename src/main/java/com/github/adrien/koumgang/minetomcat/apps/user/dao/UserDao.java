package com.github.adrien.koumgang.minetomcat.apps.user.dao;

import com.github.adrien.koumgang.minetomcat.apps.user.model.User;
import com.github.adrien.koumgang.minetomcat.apps.user.model.UserPassword;
import com.github.adrien.koumgang.minetomcat.apps.user.view.LoginHistoryView;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(String id);
    List<User> findAll();
    List<User> findAll(int page, int pageSize);
    List<User> findByUsername(String username);
    List<User> findByEmail(String email);
    List<User> findByEmailNotConfirmed();
    List<User> findActiveUsers();
    String save(User user);
    boolean update(User user);
    boolean delete(String id);
    long count();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean updatePassword(String userId, UserPassword newPassword);
    boolean addLoginHistory(String userId, LoginHistoryView loginHistoryView, Boolean failedPassword);
    List<User> findUsersWithExpiredPasswords();
    List<User> findUsersWithFailedLoginAttempts(int minAttempts);

}