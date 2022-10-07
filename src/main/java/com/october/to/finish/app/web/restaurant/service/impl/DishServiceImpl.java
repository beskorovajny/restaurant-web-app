package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.DishDAO;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.service.DishService;
import com.october.to.finish.app.web.restaurant.utils.db.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class DishServiceImpl implements DishService {
    private static final Logger LOGGER = LogManager.getLogger(DishServiceImpl.class);
    private static final String NULL_DISH_DAO_EXC = "[DishService] Can't create DishService with null input DishDAO";
    private static final String NULL_INPUT_EXC = "[DishService] Can't operate null input!";
    private static final String EXISTED_DISH_EXC =
            "[DishService] Dish with given ID: [{}] is already registered!";

    private final DishDAO dishDAO;

    public DishServiceImpl(DishDAO dishDAO) {
        if (dishDAO == null) {
            LOGGER.error(NULL_DISH_DAO_EXC);
            throw new IllegalArgumentException(NULL_DISH_DAO_EXC);
        }
        this.dishDAO = dishDAO;
    }

    @Override
    public void save(Dish dish) throws ServiceException {
        if (dish == null) {
            LOGGER.error(NULL_INPUT_EXC);
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try {
            dish.setId(dishDAO.save(dish));
            LOGGER.info("[DishService] Dish saved. (title: {})", dish.getTitle());
        } catch (DAOException e) {
            LOGGER.error("[DishService] SQLException while saving Dish (title: {}). Exc: {}"
                    , dish.getTitle(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public boolean isDishExists(Dish dish) throws ServiceException {
        try {
            if (dishDAO.findByTitle(dish.getTitle()).getId() != 0) {
                LOGGER.info(EXISTED_DISH_EXC
                        , dish.getTitle());
                return true;
            }
            return false;
        } catch (DAOException e) {
            LOGGER.error("[DishService] Dish already exist");
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Dish findById(long dishId) throws ServiceException {
        if (dishId < 1) {
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try {
            return dishDAO.findById(dishId);
        } catch (DAOException e) {
            LOGGER.error("[DishService] An exception occurs while receiving Dish. (id: {}). Exc: {}"
                    , dishId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Dish findByTitle(String title) throws ServiceException {
        if (title == null) {
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try {
            return dishDAO.findByTitle(title);
        } catch (DAOException e) {
            LOGGER.error("[DishService] An exception occurs while receiving Dish. (title: {}). Exc: {}"
                    , title, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Dish> findAll(int offset) throws ServiceException {
        try {
            return dishDAO.findAll(getOffset(offset));
        } catch (DAOException e) {
            LOGGER.error("[DishService] An exception occurs while receiving Dishes. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private int getOffset(int offset) {
        return offset * 10 - 10;
    }

    @Override
    public boolean update(long dishId, Dish dish) throws ServiceException {
        if (dishId < 1 || dish == null) {
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try {
            return dishDAO.update(dishId, dish);
        } catch (DAOException e) {
            LOGGER.error("[DishService] An exception occurs while updating Dish. (id: {}). Exc: {}"
                    , dishId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long dishId) throws ServiceException {
        if (dishId < 1) {
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try {
            dishDAO.delete(dishId);
        } catch (DAOException e) {
            LOGGER.error("[DishService] An exception occurs while deleting Dish. (id: {}). Exc: {}"
                    , dishId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public int getRecordsCount() {
        return dishDAO.countRecords();
    }
}
