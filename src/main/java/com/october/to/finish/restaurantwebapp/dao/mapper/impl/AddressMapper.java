package com.october.to.finish.restaurantwebapp.dao.mapper.impl;

import com.october.to.finish.restaurantwebapp.dao.mapper.ObjectMapper;
import com.october.to.finish.restaurantwebapp.model.Address;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AddressMapper implements ObjectMapper<Address> {

    @Override
    public Address extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, Address> addressMap = new HashMap<>();
        Address address = new Address();
        address.setId(resultSet.getLong("id"));
        address.setCountry(resultSet.getString("country"));
        address.setCity(resultSet.getString("city"));
        address.setStreet(resultSet.getString("street"));
        address.setBuildingNumber(resultSet.getString("building_number"));
        address.setRoomNumber(resultSet.getString("room_number"));

        addressMap.put(String.valueOf(address.getId()), address);

        address = this.makeUnique(addressMap, address);
        return address;
    }

    @Override
    public Address makeUnique(Map<String, Address> cache, Address address) {
        cache.putIfAbsent(String.valueOf(address.getId()), address);
        return cache.get(String.valueOf(address.getId()));
    }
}
