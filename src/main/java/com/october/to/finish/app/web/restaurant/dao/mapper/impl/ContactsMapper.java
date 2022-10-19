package com.october.to.finish.app.web.restaurant.dao.mapper.impl;

import com.october.to.finish.app.web.restaurant.dao.mapper.ObjectMapper;
import com.october.to.finish.app.web.restaurant.model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ContactsMapper implements ObjectMapper<Contacts> {
    @Override
    public Contacts extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, Contacts> addressMap = new HashMap<>();
        Contacts contacts = new Contacts();
        contacts.setId(resultSet.getLong("id"));
        contacts.setCountry(resultSet.getString("country"));
        contacts.setCity(resultSet.getString("city"));
        contacts.setStreet(resultSet.getString("street"));
        contacts.setBuildingNumber(resultSet.getString("building_number"));
        contacts.setPhone(resultSet.getString("phone"));

        addressMap.put(String.valueOf(contacts.getId()), contacts);

        contacts = this.makeUnique(addressMap, contacts);
        return contacts;
    }

    @Override
    public Contacts makeUnique(Map<String, Contacts> cache, Contacts contacts) {
        cache.putIfAbsent(String.valueOf(contacts.getId()), contacts);
        return cache.get(String.valueOf(contacts.getId()));
    }

    public void setAddressParams(Contacts contacts, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, contacts.getCountry());
        preparedStatement.setString(2, contacts.getCity());
        preparedStatement.setString(3, contacts.getStreet());
        preparedStatement.setString(4, contacts.getBuildingNumber());
        preparedStatement.setString(5, contacts.getPhone());
    }

    public List<Contacts> extractAddresses(List<Contacts> contacts, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Optional<Contacts> address = Optional.
                    ofNullable(extractFromResultSet(resultSet));
            address.ifPresent(contacts::add);
        }
        return contacts;
    }
}
