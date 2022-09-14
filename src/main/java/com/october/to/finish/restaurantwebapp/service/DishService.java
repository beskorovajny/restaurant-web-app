package com.october.to.finish.restaurantwebapp.service;

import com.october.to.finish.restaurantwebapp.exceptions.ServiceException;
import com.october.to.finish.restaurantwebapp.model.Dish;

import java.util.List;

public interface DishService {
    long save(Dish dish) throws ServiceException;

    Dish findById(long dishId) throws ServiceException;

    Dish findByTitle(String title) throws ServiceException;

    List<Dish> findAll() throws ServiceException;

    boolean update(long dishId, Dish dish) throws ServiceException;

    void delete(long dishId) throws ServiceException;
}
