package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.DishDAO;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Dish;

import java.sql.Connection;
import java.util.Set;

public class DishDAOImpl implements DishDAO {
    private final Connection connection;
    public DishDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertDish(Dish dish) throws DAOException {
        return false;
    }

    @Override
    public boolean deleteDish(long dishId) throws DAOException {
        return false;
    }

    @Override
    public boolean updateDish(long dishId , Dish dish) throws DAOException {
        return false;
    }

    @Override
    public Dish getDishById(long dishId) throws DAOException {
        return null;
    }

    @Override
    public Dish getDishByTitle(String title) throws DAOException {
        return null;
    }

    @Override
    public Set<Dish> findAllDishes() throws DAOException {
        return null;
    }
}
