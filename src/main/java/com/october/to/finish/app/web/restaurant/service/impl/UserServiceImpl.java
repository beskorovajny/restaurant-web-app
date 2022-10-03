package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.UserDAO;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.service.UserService;
import com.october.to.finish.app.web.restaurant.utils.db.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private static final String NULL_USER_DAO_EXC = "[UserService] Can't create UserService with null input UserDAO";
    private static final String NULL_USER_INPUT_EXC = "[UserService] Can't operate null input!";
    private static final String REGISTERED_EMAIL_EXC =
            "[UserService] User with given email is already registered! (email: {})";
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        if (userDAO == null) {
            LOGGER.error(NULL_USER_DAO_EXC);
            throw new IllegalArgumentException(NULL_USER_DAO_EXC);
        }
        this.userDAO = userDAO;
    }

    @Override
    public boolean save(User user) throws ServiceException {
        if (user == null) {
            LOGGER.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            return checkAndSave(user);
        } catch (SQLException e) {
            LOGGER.error("[UserService] SQLException while saving User (email: {}). Exc: {}"
                    , user.getEmail(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private boolean checkAndSave(User user) throws ServiceException, SQLException {
        userDAO.getConnection().setAutoCommit(false);
        try {
            if (userDAO.findByEmail(user.getEmail()).getId() != 0) {
                DBUtils.rollback(userDAO.getConnection());
                LOGGER.error(REGISTERED_EMAIL_EXC
                        , user.getEmail());
                throw new ServiceException(REGISTERED_EMAIL_EXC);
            }
            user.setId(userDAO.save(user));
            userDAO.getConnection().commit();
            userDAO.getConnection().setAutoCommit(true);
            LOGGER.info("[UserService] User saved. (email: {})", user.getEmail());
            return true;
        } catch (DAOException e) {
            userDAO.getConnection().rollback();
            LOGGER.error("[UserService] Connection rolled back while saving User. (email: {}). Exc: {}"
                    , user.getEmail(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public User findById(long id) throws ServiceException {
        if (id < 1) {
            LOGGER.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            return userDAO.findById(id);
        } catch (DAOException e) {
            LOGGER.error("[UserService] An exception occurs while receiving User. (id: {}). Exc: {}"
                    , id, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User findByEmail(String eMail) throws ServiceException {
        if (eMail == null) {
            LOGGER.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            return userDAO.findByEmail(eMail);
        } catch (DAOException e) {
            LOGGER.error("[UserService] An exception occurs while receiving User. (email: {}). Exc: {}"
                    , eMail, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDAO.findAll();
        } catch (DAOException e) {
            LOGGER.error("[UserService] An exception occurs while receiving Users. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(long userId, User user) throws ServiceException {
        if (userId < 1 || user == null) {
            LOGGER.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            return userDAO.update(userId, user);
        } catch (DAOException e) {
            LOGGER.error("[UserService] An exception occurs while updating User. (id: {}). Exc: {}"
                    , userId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long userId) throws ServiceException {
        if (userId < 1) {
            LOGGER.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            LOGGER.error("[UserService] An exception occurs while deleting User. (id: {}). Exc: {}"
                    , userId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
