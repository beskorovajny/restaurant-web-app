package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.UserDAO;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * This class implements business logic for {@link User}
 */
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private static final String NULL_USER_DAO_EXC = "[UserService] Can't create UserService with null input UserDAO";
    private static final String NULL_USER_INPUT_EXC = "[UserService] Can't operate null (or < 1) input!";
    private static final String REGISTERED_EMAIL_EXC =
            "[UserService] User with given email is already registered! (email:) [{}]";
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        if (userDAO == null) {
            LOGGER.error(NULL_USER_DAO_EXC);
            throw new IllegalArgumentException(NULL_USER_DAO_EXC);
        }
        this.userDAO = userDAO;
    }

    @Override
    public void save(User user) throws ServiceException {
        if (user == null) {
            LOGGER.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            user.setId(userDAO.save(user));
            LOGGER.info("[UserService] User saved. (email: {})", user.getEmail());
        } catch (DAOException e) {
            LOGGER.error("[UserService] SQLException while saving User (email: {}). Exc: {}"
                    , user.getEmail(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public boolean isUserExist(User user) throws ServiceException {
        try {
            if (userDAO.findByEmail(user.getEmail()).getId() != 0) {
                LOGGER.info(REGISTERED_EMAIL_EXC, user.getEmail());
                return true;
            }
            return false;
        } catch (DAOException e) {
            LOGGER.error("[UserService] User is exists.");
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
    public List<User> findAll(int offset) throws ServiceException {
        try {
            return userDAO.findAll(getOffset(offset));
        } catch (DAOException e) {
            LOGGER.error("[UserService] An exception occurs while receiving Users. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * @param offset is a current page value
     * @return value for pagination on JSP for {@link User}
     */
    private int getOffset(int offset) {
        return offset * 10 - 10;
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

    /**
     * @return all {@link User} records in database
     */
    public int getRecordsCount() {
        return userDAO.countRecords();
    }
}
