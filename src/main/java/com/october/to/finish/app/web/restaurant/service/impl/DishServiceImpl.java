package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.DishDAO;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.service.DishService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * This class implements business logic for {@link Dish}
 */
public class DishServiceImpl implements DishService {
    private static final Logger log = LogManager.getLogger(DishServiceImpl.class);
    private static final String NULL_DISH_DAO_EXC = "[DishService] Can't create DishService with null input DishDAO";
    private static final String NULL_INPUT_EXC = "[DishService] Can't operate null input!";
    private static final String EXISTED_DISH_EXC =
            "[DishService] Dish with given ID: [{}] is already registered!";

    private final DishDAO dishDAO;

    public DishServiceImpl(DishDAO dishDAO) {
        if (dishDAO == null) {
            log.error(NULL_DISH_DAO_EXC);
            throw new IllegalArgumentException(NULL_DISH_DAO_EXC);
        }
        this.dishDAO = dishDAO;
    }

    @Override
    public void save(Dish dish) throws ServiceException {
        if (dish == null) {
            log.error(NULL_INPUT_EXC);
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try {
            dish.setId(dishDAO.save(dish));
            log.info("[DishService] Dish saved. (title: {})", dish.getTitle());
        } catch (DAOException e) {
            log.error("[DishService] SQLException while saving Dish (title: {}). Exc: {}"
                    , dish.getTitle(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isDishExists(Dish dish) throws ServiceException {
        try {
            if (dishDAO.findByTitle(dish.getTitle()).getId() != 0) {
                log.info(EXISTED_DISH_EXC
                        , dish.getTitle());
                return true;
            }
            return false;
        } catch (DAOException e) {
            log.error("[DishService] Dish already exist");
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
            log.error("[DishService] An exception occurs while receiving Dish. (id: {}). Exc: {}"
                    , dishId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Dish findByTitle(String title) throws ServiceException {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException(NULL_INPUT_EXC);
        }
        try {
            return dishDAO.findByTitle(title);
        } catch (DAOException e) {
            log.error("[DishService] An exception occurs while receiving Dish. (title: {}). Exc: {}"
                    , title, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Dish> findAll(int offset) throws ServiceException {
        try {
            return dishDAO.findAll(getOffset(offset));
        } catch (DAOException e) {
            log.error("[DishService] An exception occurs while receiving Dishes. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Dish> findAllSortedByPrice(int offset) throws ServiceException {
        try {
            return dishDAO.findAllSortedByPrice(getOffset(offset));
        } catch (DAOException e) {
            log.error("[DishService] An exception occurs while receiving sorted by price Dishes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Dish> findAllSortedByTitle(int offset) throws ServiceException {
        try {
            return dishDAO.findAllSortedByTitle(getOffset(offset));
        } catch (DAOException e) {
            log.error("[DishService] An exception occurs while receiving sorted by title Dishes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Dish> findAllSortedByCategory(int offset) throws ServiceException {
        try {
            return dishDAO.findAllSortedByCategory(getOffset(offset));
        } catch (DAOException e) {
            log.error("[DishService] An exception occurs while receiving sorted by category Dishes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Dish> findAllFilteredByCategory(long categoryId, int offset) throws ServiceException {
        try {
            return dishDAO.findAllFilteredByCategory(categoryId, getOffset(offset));
        } catch (DAOException e) {
            log.error("[DishService] An exception occurs while receiving filtered by category Dishes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * @param offset is a current page value
     * @return value for pagination on JSP for {@link Dish}
     */
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
            log.error("[DishService] An exception occurs while updating Dish. (id: {}). Exc: {}"
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
            log.error("[DishService] An exception occurs while deleting Dish. (id: {}). Exc: {}"
                    , dishId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * @return all {@link Dish} records in database
     */
    public int getRecordsCount() {
        return dishDAO.countRecords();
    }

    /**
     * @param categoryId is a value of {@link Dish.Category#id}
     * @return value of records count in database
     */
    public int getRecordsCountForCategory(long categoryId) {
        return dishDAO.countRecordsForCategory(categoryId);
    }
}
