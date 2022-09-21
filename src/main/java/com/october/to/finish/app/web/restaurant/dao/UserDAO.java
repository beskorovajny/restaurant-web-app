package com.october.to.finish.app.web.restaurant.dao;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserDAO {
    Connection getConnection();

    long save(User user) throws DAOException;

    User findById(long userId) throws DAOException;

    User findByEmail(String eMail) throws DAOException;

    List<User> findAll() throws DAOException;

    boolean update(long userId, User user) throws DAOException;

    void delete(long userId) throws DAOException;
}
