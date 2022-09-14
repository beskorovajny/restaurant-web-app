package com.october.to.finish.restaurantwebapp.service.impl;

import com.october.to.finish.restaurantwebapp.exceptions.ServiceException;
import com.october.to.finish.restaurantwebapp.model.Dish;
import com.october.to.finish.restaurantwebapp.service.DishService;

import java.util.List;

public class DishServiceImpl implements DishService {
    @Override
    public long save(Dish dish) throws ServiceException {
        return 0;
    }

    @Override
    public Dish findById(long dishId) throws ServiceException {
        return null;
    }

    @Override
    public Dish findByTitle(String title) throws ServiceException {
        return null;
    }

    @Override
    public List<Dish> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(long dishId, Dish dish) throws ServiceException {
        return false;
    }

    @Override
    public void delete(long dishId) throws ServiceException {

    }
}
