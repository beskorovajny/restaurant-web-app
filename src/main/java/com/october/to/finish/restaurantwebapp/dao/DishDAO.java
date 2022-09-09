package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Dish;

import java.util.List;

public interface DishDAO {
    long save(Dish dish) throws DAOException;

    Dish findById(long dishId) throws DAOException;

    Dish findByTitle(String title) throws DAOException;

    List<Dish> findAll() throws DAOException;

    boolean update(long dishId, Dish dish) throws DAOException;

    void delete(long dishId) throws DAOException;


}
