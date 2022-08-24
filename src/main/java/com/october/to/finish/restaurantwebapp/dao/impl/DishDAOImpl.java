package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.DishDAO;
import com.october.to.finish.restaurantwebapp.exceptions.DBException;
import com.october.to.finish.restaurantwebapp.model.Dish;

import java.sql.Connection;
import java.util.Set;

public class DishDAOImpl implements DishDAO {
    private final Connection connection;
    public DishDAOImpl(Connection connection) {
        this.connection = connection;
    }

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
    public Set<Dish> findAllDishes() throws DBException {
        return null;
    }
}
