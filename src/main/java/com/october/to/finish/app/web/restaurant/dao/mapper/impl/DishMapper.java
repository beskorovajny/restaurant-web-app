package com.october.to.finish.app.web.restaurant.dao.mapper.impl;

import com.october.to.finish.app.web.restaurant.dao.mapper.ObjectMapper;
import com.october.to.finish.app.web.restaurant.model.Dish;

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
                setDescription(resultSet.getString("description")).
                setPrice(resultSet.getBigDecimal("price").doubleValue()).
                setWeight(resultSet.getInt("weight")).
                setCooking(resultSet.getInt("cooking")).
                setDateCreated(resultSet.getTimestamp("created").toLocalDateTime()).
                setCategory(getById(resultSet.getLong("category_id"))).
                build();


        dishMap.put(String.valueOf(dish.getId()), dish);

        dish = this.makeUnique(dishMap, dish);
        return dish;
    }

    private Dish.Category getById(Long id) {
        for (Dish.Category c : Dish.Category.values()) {
            if (c.getId() == (id)) return c;
        }
        return null;
    }

    @Override
    public Dish makeUnique(Map<String, Dish> cache, Dish dish) {
        cache.putIfAbsent(String.valueOf(dish.getId()), dish);
        return cache.get(String.valueOf(dish.getId()));
    }

    public void setDishParams(Dish dish, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, dish.getTitle());
        preparedStatement.setString(2, dish.getDescription());
        preparedStatement.setBigDecimal(3, BigDecimal.valueOf(dish.getPrice()));
        preparedStatement.setInt(4, dish.getWeight());
        preparedStatement.setInt(5, dish.getCooking());
        preparedStatement.setTimestamp(6, Timestamp.valueOf(dish.getDateCreated()));
        preparedStatement.setLong(7, dish.getCategory().getId());
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
