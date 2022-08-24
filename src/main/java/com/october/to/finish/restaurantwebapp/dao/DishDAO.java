package com.october.to.finish.restaurantwebapp.dao;

import com.october.to.finish.restaurantwebapp.exceptions.DBException;
import com.october.to.finish.restaurantwebapp.model.Dish;

import java.util.List;

public interface DishDAO extends GenericDAO<Dish> {
    boolean insertDish(Dish dish) throws DBException;

    boolean deleteDish(Dish dish) throws DBException;

    boolean updateDish(Dish dish) throws DBException;

    Dish getDishById(long dishId) throws DBException;

    Dish getDishByTitle(String title) throws DBException;

    List<Dish> findAllDishes() throws DBException;
}
