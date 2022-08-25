package com.october.to.finish.restaurantwebapp.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * The interface Object mapper.
 *
 * @param <T> the type parameter
 * @author besko
 */
public interface ObjectMapper<T> {

    T extractFromResultSet(ResultSet resultSet) throws SQLException;

    T makeUnique(Map<Integer, T> cache, T object);

}
