package com.october.to.finish.restaurantwebapp.dao.mapper.impl;

import com.october.to.finish.restaurantwebapp.dao.mapper.ObjectMapper;
import com.october.to.finish.restaurantwebapp.model.Address;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public void setAddressParams(Address address, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, address.getCountry());
        preparedStatement.setString(2, address.getCity());
        preparedStatement.setString(3, address.getStreet());
        preparedStatement.setString(4, address.getBuildingNumber());
        preparedStatement.setString(5, address.getRoomNumber());
    }
    public List<Address> extractAddresses(List<Address> addresses, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Optional<Address> address = Optional.
                    ofNullable(extractFromResultSet(resultSet));
            address.ifPresent(addresses::add);
        }
        return addresses;
    }
}
