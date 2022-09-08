package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends GenericDAO<User> {
    boolean insertUser(User user) throws DAOException, SQLException;
    User getUserById(long userId) throws DAOException;
    User getUserByEmail(String eMail) throws DAOException;
    List<User> findAllUsers() throws DAOException;
    boolean updateUser(long userId, User user) throws DAOException;
    boolean deleteUser(long userId) throws DAOException;
}
