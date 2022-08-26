package com.october.to.finish.restaurantwebapp.dao.mapper.impl;

import com.october.to.finish.restaurantwebapp.dao.mapper.ObjectMapper;
import com.october.to.finish.restaurantwebapp.model.Dish;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DishMapper implements ObjectMapper<Dish> {
    @Override
    public Dish extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, Dish> dishMap = new HashMap<>();
        Dish dish = Dish.newBuilder().setId(resultSet.getLong("id")).
                setTitle(resultSet.getString("title")).
                setTitleCyrillic(resultSet.getString("title_ukr")).
                setDescription(resultSet.getString("description")).
                setDescriptionCyrillic(resultSet.getString("description_ukr")).
                setPrice(resultSet.getBigDecimal("price").doubleValue()).
                setWeightInGrams(resultSet.getInt("weight")).
                setCount(resultSet.getInt("count")).
                setMinutesToCook(resultSet.getInt("minutes_to_cook")).
                setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime()).
                setImage(resultSet.getBytes("image")).setCategory(Dish.Category.PIZZA).
                build();


        dishMap.put(String.valueOf(dish.getId()), dish);

        dish = this.makeUnique(dishMap, dish);
        return dish;
    }

    @Override
    public Dish makeUnique(Map<String, Dish> cache, Dish dish) {
        cache.putIfAbsent(String.valueOf(dish.getId()), dish);
        return cache.get(String.valueOf(dish.getId()));
    }
}
