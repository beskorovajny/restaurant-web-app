package com.october.to.finish.restaurantwebapp.service.impl;

import com.october.to.finish.restaurantwebapp.dao.UserDAO;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.exceptions.ServiceException;
import com.october.to.finish.restaurantwebapp.model.User;
import com.october.to.finish.restaurantwebapp.service.UserService;
import com.october.to.finish.restaurantwebapp.utils.DBUtils;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO)   {
        this.userDAO = userDAO;
    }

    @Override
    public boolean save(User user) throws ServiceException {
        try {
            return checkAndSave(user);
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private boolean checkAndSave(User user) throws ServiceException, SQLException {
        userDAO.getConnection().setAutoCommit(false);
        try {
            if (userDAO.findByEmail(user.getEmail()).getId() != 0) {
                DBUtils.rollback(userDAO.getConnection());
                throw new ServiceException("User with given email is already registered!");
            } else {
                userDAO.save(user);
            }
            userDAO.getConnection().commit();
            userDAO.getConnection().setAutoCommit(true);
            return true;
        } catch (DAOException e) {
            userDAO.getConnection().rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public User findById(long userId) throws ServiceException {
        try {
            return userDAO.findById(userId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User findByEmail(String eMail) throws ServiceException {
        try {
            return userDAO.findByEmail(eMail);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(User user) throws ServiceException {
        try {
            return userDAO.update(user.getId(), user);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long userId) throws ServiceException {
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
