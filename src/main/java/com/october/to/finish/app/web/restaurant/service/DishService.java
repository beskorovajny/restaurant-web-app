package com.october.to.finish.app.web.restaurant.service;

import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Dish;

import java.util.List;

public interface DishService {
    void save(Dish dish) throws ServiceException;

    Dish findById(long dishId) throws ServiceException;

    Dish findByTitle(String title) throws ServiceException;

    List<Dish> findAll(int offset) throws ServiceException;

    List<Dish> findAllSortedByPrice(int offset) throws ServiceException;

    List<Dish> findAllSortedByTitle(int offset) throws ServiceException;

    List<Dish> findAllSortedByCategory(int offset) throws ServiceException;

    List<Dish> findAllFilteredByCategory(long categoryId, int offset) throws ServiceException;

    boolean update(long dishId, Dish dish) throws ServiceException;

    void delete(long dishId) throws ServiceException;

    int getRecordsCount();

    int getRecordsCountForCategory(long categoryId);

    boolean isDishExists(Dish dish) throws ServiceException;
}
