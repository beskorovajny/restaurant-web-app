package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.DishDAO;
import com.october.to.finish.restaurantwebapp.exceptions.DBException;
import com.october.to.finish.restaurantwebapp.model.Dish;

import java.util.List;

public class DishDAOImpl implements DishDAO {
    @Override
    public boolean insertDish(Dish dish) throws DBException {
        return false;
    }

    @Override
    public boolean deleteDish(Dish dish) throws DBException {
        return false;
    }

    @Override
    public boolean updateDish(Dish dish) throws DBException {
        return false;
    }

    @Override
    public Dish getDishById(long dishId) throws DBException {
        return null;
    }

    @Override
    public Dish getDishByTitle(String title) throws DBException {
        return null;
    }

    @Override
    public List<Dish> findAllDishes() throws DBException {
        return null;
    }
}
