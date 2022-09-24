package com.october.to.finish.app.web.restaurant.service;

import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Dish;

import java.util.List;

public interface DishService {
    boolean save(Dish dish) throws ServiceException;

    Dish findById(long dishId) throws ServiceException;

    Dish findByTitle(String title) throws ServiceException;

    List<Dish> findAll(int offset) throws ServiceException;

    boolean update(long dishId, Dish dish) throws ServiceException;

    void delete(long dishId) throws ServiceException;

    int getRecordsCount();
}
