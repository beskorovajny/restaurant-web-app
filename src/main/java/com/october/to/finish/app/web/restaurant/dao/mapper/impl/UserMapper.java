package com.october.to.finish.app.web.restaurant.dao.mapper.impl;

import com.october.to.finish.app.web.restaurant.dao.mapper.ObjectMapper;
import com.october.to.finish.app.web.restaurant.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * This class implements functionality of
 * @see com.october.to.finish.app.web.restaurant.model.User
 * mapping using JDBC API.
 */
public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, User> usersMap = new HashMap<>();
        User user = User.newBuilder()
                .setId(resultSet.getLong("id"))
                .setEmail(resultSet.getString("email"))
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("last_name"))
                .setRole(getById(resultSet.getLong("role_id")))
                .setPassword(resultSet.getString("password").toCharArray())
                .build();

        usersMap.put(String.valueOf(user.getId()), user);

        user = this.makeUnique(usersMap, user);
        return user;
    }

    @Override
    public User makeUnique(Map<String, User> cache, User user) {
        cache.putIfAbsent(String.valueOf(user.getId()), user);
        return cache.get(String.valueOf(user.getId()));
    }

    /**
     * @param id long value parameter of User.Role.id
     * @return User.Role value if role is exists or null value otherwise
     * @see User.Role
     */
    private User.Role getById(Long id) {
        for (User.Role r : User.Role.values()) {
            if (r.getId() == (id)) return r;
        }
        return null;
    }

    public void setUserParams(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getFirstName());
        preparedStatement.setString(3, user.getLastName());
        preparedStatement.setString(4, String.valueOf(user.getPassword()));
        preparedStatement.setInt(5, user.getRole().getId());
    }

    public List<User> extractUsers(List<User> users, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Optional<User> user = Optional.
                    ofNullable(extractFromResultSet(resultSet));
            user.ifPresent(users::add);
        }
        return users;
    }
}
