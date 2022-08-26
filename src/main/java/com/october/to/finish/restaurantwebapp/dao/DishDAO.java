package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Dish;

import java.util.Set;

public interface DishDAO extends GenericDAO<Dish> {
    boolean insertDish(Dish dish) throws DAOException;

    boolean deleteDish(long dishId) throws DAOException;

    boolean updateDish(long dishId, Dish dish) throws DAOException;

    Dish getDishById(long dishId) throws DAOException;

    Dish getDishByTitle(String title) throws DAOException;

    Set<Dish> findAllDishes() throws DAOException;
}
