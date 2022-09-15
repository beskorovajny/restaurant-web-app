package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.User;

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
