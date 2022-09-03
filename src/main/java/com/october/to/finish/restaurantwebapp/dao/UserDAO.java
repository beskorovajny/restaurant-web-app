package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends GenericDAO<User> {
    boolean insertUser(User user) throws DAOException, SQLException;

    boolean deleteUser(long personId) throws DAOException;

    boolean updateUser(long personId, User user) throws DAOException;

    User getUserById(long personId) throws DAOException;

    User getUserByEmail(String eMail) throws DAOException;

    List<User> findAllUsers() throws DAOException;
}
