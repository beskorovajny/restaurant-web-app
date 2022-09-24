package com.october.to.finish.app.web.restaurant.dao;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Dish;

import java.sql.Connection;
import java.util.List;

public interface DishDAO {
    Connection getConnection();

    long save(Dish dish) throws DAOException;

    Dish findById(long dishId) throws DAOException;

    Dish findByTitle(String title) throws DAOException;

    List<Dish> findAll(int offset) throws DAOException;

    boolean update(long dishId, Dish dish) throws DAOException;

    void delete(long dishId) throws DAOException;

    int countRecords();
}
