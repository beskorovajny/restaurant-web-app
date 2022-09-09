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
    private static final String INSERT = "INSERT INTO address" +
            " (country, city, street, building_number, room_number, user_id)" +
            " VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM address WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM address";
    private static final String UPDATE = "UPDATE address SET country = ?," +
            " city = ?, street = ?, building_number = ?, room_number = ? WHERE id = ?";
    private static final String UPDATE_BY_USER_ID = "UPDATE address SET country = ?," +
            " city = ?, street = ?, building_number = ?, room_number = ? WHERE user_id = ?";
    private static final String DELETE = "DELETE FROM address WHERE id = ?";
    private static final String DELETE_BY_USER_ID = "DELETE FROM address WHERE user_id = ?";

    private final Connection connection;

    private final AddressMapper addressMapper = new AddressMapper();

    public AddressDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long save(long userId, Address address) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(INSERT)) {
            addressMapper.setAddressParams(address, preparedStatement);
            preparedStatement.setLong(6, userId);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long key = 0;
            if (resultSet.next()) {
                key = resultSet.getLong(1);
                LOGGER.info("Address : {} was saved successfully", address);
            }
            return key;
        } catch (SQLException e) {
            LOGGER.error("Address : [{}] was not saved. An exception occurs : {}",
                    address, e.getMessage());
            throw new DAOException("[AddressDAO] exception while saving Address" + e.getMessage(), e);
        }
    }

    @Override
    public Address findById(long addressId) throws DAOException {
        Optional<Address> address = Optional.empty();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, addressId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                address = Optional.ofNullable(addressMapper.extractFromResultSet(resultSet));
            }
            address.ifPresent(addr -> LOGGER.info("Address received from db: [{}]", addr));
        } catch (SQLException e) {
            LOGGER.error("Address with ID : [{}] was not found. An exception occurs : {}", addressId, e.getMessage());
            throw new DAOException("[AddressDAO] exception while receiving Address", e);
        }
        return address.orElse(new Address());
    }

    @Override
    public List<Address> findAll() throws DAOException {
        List<Address> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(FIND_ALL)) {
            addressMapper.extractAddresses(result, preparedStatement);
            if (!result.isEmpty()) {
                LOGGER.info("Address was found successfully.");
                return result;
            }
        } catch (SQLException e) {
            LOGGER.error("Address was not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[AddressDAO] exception while receiving all addresses", e);
        }
        return result;
    }

    @Override
    public boolean update(long addressId, Address address) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE)) {
            addressMapper.setAddressParams(address, preparedStatement);
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
        LOGGER.info("Address with ID : [{}] was not found for update", addressId);
        return false;
    }

    @Override
    public boolean updateByUserId(long userId, Address address) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(UPDATE_BY_USER_ID)) {
            addressMapper.setAddressParams(address, preparedStatement);
            preparedStatement.setLong(6, userId);

            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated > 0 && rowUpdated < 6) {
                LOGGER.info("Address for UserID : [{}] was updated.", userId);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("Address for UserID : [{}] was not updated. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[AddressDAO] exception while updating Address" + e.getMessage(), e);
        }
        LOGGER.info("Address for UserID : [{}] was not  found for update", userId);
        return false;
    }

    @Override
    public void delete(long addressId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE)) {
            preparedStatement.setLong(1, addressId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                LOGGER.info("Address with ID : [{}] was removed.", addressId);
            } else {
                LOGGER.info("Address with ID : [{}] was not found to remove.", addressId);
            }
        } catch (SQLException e) {
            LOGGER.error("Address with ID : [{}] was not removed. An exception occurs : {}",
                    addressId, e.getMessage());
            throw new DAOException("[AddressDAO] exception while removing Address" + e.getMessage(), e);
        }
    }

    public void deleteByUserId(long userId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(DELETE_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            int rowDeleted = preparedStatement.executeUpdate();
            if (rowDeleted > 0) {
                LOGGER.info("Address for UserID : [{}] was removed.", userId);
            } else {
                LOGGER.info("Address for UserID : [{}] was not found to remove.", userId);
            }
        } catch (SQLException e) {
            LOGGER.error("Address for UserID : [{}] was not removed. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[AddressDAO] exception while removing Address" + e.getMessage(), e);
        }
    }
}
