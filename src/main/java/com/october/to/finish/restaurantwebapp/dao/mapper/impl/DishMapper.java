package com.october.to.finish.restaurantwebapp.dao.mapper.impl;

import com.october.to.finish.restaurantwebapp.dao.mapper.ObjectMapper;
import com.october.to.finish.restaurantwebapp.model.Dish;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                setImage(resultSet.getBytes("image")).
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

    public void setDishParams(Dish dish, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, dish.getTitle());
        preparedStatement.setString(2, dish.getTitleCyrillic());
        preparedStatement.setString(3, dish.getDescription());
        preparedStatement.setString(4, dish.getDescriptionCyrillic());
        preparedStatement.setBigDecimal(5, BigDecimal.valueOf(dish.getPrice()));
        preparedStatement.setInt(6, dish.getWeightInGrams());
        preparedStatement.setInt(7, dish.getCount());
        preparedStatement.setInt(8, dish.getMinutesToCook());
        preparedStatement.setTimestamp(9, Timestamp.valueOf(dish.getDateCreated()));
        preparedStatement.setBytes(10, dish.getImage());
        preparedStatement.setLong(11, dish.getCategory().getId());
    }

    public List<Dish> extractDishes(List<Dish> dishes, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Optional<Dish> dish = Optional.
                    ofNullable(extractFromResultSet(resultSet));
            dish.ifPresent(dishes::add);
        }
        return dishes;
    }
}
