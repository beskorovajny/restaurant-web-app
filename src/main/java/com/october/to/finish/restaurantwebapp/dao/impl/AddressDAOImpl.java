package com.october.to.finish.restaurantwebapp.dao.impl;

import com.october.to.finish.restaurantwebapp.dao.AddressDAO;
import com.october.to.finish.restaurantwebapp.dao.mapper.impl.AddressMapper;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressDAOImpl implements AddressDAO {

    private static final Logger LOGGER = LogManager.getLogger(AddressDAOImpl.class);
    private static final String INSERT_ADDRESS = "INSERT INTO address" +
            " (country, city, street, building_number, room_number, user_id)" +
            " VALUES (?, ?, ?, ?, ?, ?);";
    private static final String DELETE_ADDRESS = "DELETE FROM address WHERE id = ?";
    private static final String UPDATE_ADDRESS = "UPDATE address SET country = ?," +
            " city = ?, street = ?, building_number = ?, room_number = ? WHERE id = ?";

    private static final String FIND_BY_ID = "SELECT * FROM address WHERE id = ?";
    private static final String FIND_ALL_ADDRESSES = "SELECT * FROM address";
    private final Connection connection;

    public AddressDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertAddress(long personId, Address address) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT_ADDRESS)) {
            setAddressParams(address, preparedStatement);
            preparedStatement.setLong(6, personId);

            preparedStatement.executeUpdate();
            LOGGER.info("Address : {} was inserted successfully", address);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Address : [{}] was not inserted. An exception occurs : {}",
                    address, e.getMessage());
            throw new DAOException("[AddressDAO] exception while creating Address" + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteAddress(long addressId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE_ADDRESS)) {
            preparedStatement.setLong(1, addressId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                LOGGER.info("Address with ID : [{}] was removed.", addressId);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("Address with ID : [{}] was not removed. An exception occurs : {}",
                    addressId, e.getMessage());
            throw new DAOException("[AddressDAO] exception while removing Address" + e.getMessage(), e);
        }
        LOGGER.info("Address with ID : [{}] was not removed.", addressId);
        return false;
    }

    @Override
    public boolean updateAddress(long addressId, Address address) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE_ADDRESS)) {
            setAddressParams(address, preparedStatement);

            preparedStatement.setLong(6, addressId);

            int rowUpdated = preparedStatement.executeUpdate();

            if (rowUpdated > 0 && rowUpdated < 6) {
                LOGGER.info("Address with ID : [{}] was updated.", addressId);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("Address with ID : [{}] was not updated. An exception occurs : {}",
                    addressId, e.getMessage());
            throw new DAOException("[AddressDAO] exception while updating Address" + e.getMessage(), e);
        }
        LOGGER.info("Address with ID : [{}] was not  found for update", addressId);
        return false;
    }

    private void setAddressParams(Address address, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, address.getCountry());
        preparedStatement.setString(2, address.getCity());
        preparedStatement.setString(3, address.getStreet());
        preparedStatement.setString(4, address.getBuildingNumber());
        preparedStatement.setString(5, address.getRoomNumber());
    }

    @Override
    public Address getAddressById(long addressId) throws DAOException {
        Optional<Address> address = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_ID)) {

            preparedStatement.setLong(1, addressId);
            ResultSet resultSet = preparedStatement.executeQuery();
            AddressMapper addressMapper = new AddressMapper();
            if (resultSet.next()) {
                address = Optional.ofNullable(addressMapper.extractFromResultSet(resultSet));
            }
            address.ifPresent(addr -> LOGGER.info("Loaded address from db: [{}]", addr));
        } catch (SQLException e) {
            LOGGER.error("Address with ID : [{}] was not found. An exception occurs : {}", addressId, e.getMessage());
            throw new DAOException("[AddressDAO] exception while loading Address", e);
        }
        return address.orElse(new Address());
    }

    @Override
    public List<Address> findAllAddresses() throws DAOException {
        List<Address> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL_ADDRESSES)) {
            extractAddresses(result, preparedStatement);
            if (!result.isEmpty()) {
                LOGGER.info("Address was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("Address was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[AddressDAO] exception while reading all addresses", e);
        }
        return result;
    }

    private List<Address> extractAddresses(List<Address> addresses, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        AddressMapper addressMapper = new AddressMapper();

        while (resultSet.next()) {
            Optional<Address> address = Optional.
                    ofNullable(addressMapper.extractFromResultSet(resultSet));
            address.ifPresent(addresses::add);
        }
        return addresses;
    }
}
