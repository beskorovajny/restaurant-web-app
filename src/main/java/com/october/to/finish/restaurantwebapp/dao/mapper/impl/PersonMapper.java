package com.october.to.finish.restaurantwebapp.dao.mapper.impl;

import com.october.to.finish.restaurantwebapp.dao.mapper.ObjectMapper;
import com.october.to.finish.restaurantwebapp.model.Address;
import com.october.to.finish.restaurantwebapp.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PersonMapper implements ObjectMapper<Person> {
    @Override
    public Person extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, Person> usersMap = new HashMap<>();
        Person person = Person.newBuilder()
                .setId(resultSet.getLong("id"))
                .setEmail(resultSet.getString("email"))
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("last_name"))
                .setPhoneNumber(resultSet.getString("phone_number"))
                .setPassword(resultSet.getString("password").toCharArray())
                .build();

        usersMap.put(String.valueOf(person.getId()), person);

        person = this.makeUnique(usersMap, person);
        return person;
    }

    @Override
    public Person makeUnique(Map<String, Person> cache, Person person) {
        cache.putIfAbsent(String.valueOf(person.getId()), person);
        return cache.get(String.valueOf(person.getId()));
    }
}
