package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.dao.mapper.impl.AddressMapper;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressDAOImplTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private AddressMapper addressMapper;
    @InjectMocks
    private AddressDAOImpl addressDAO;
    private Address expected;
    private final long addressId = 100;

    @BeforeEach
    public void setUp() {
        expected = new Address("Country", "City", "Street", "Building");
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    void shouldNotInjectTest() {
        assertThrows(IllegalArgumentException.class, () -> new AddressDAOImpl(null));
    }

    @Test
    void getConnectionTest() {
        assertEquals(connection, addressDAO.getConnection());
    }

    @Test
    void shouldSaveTest() throws SQLException, DAOException {
        final long receiptId = 1L;

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(anyInt())).thenReturn(1L);
        addressDAO.save(receiptId, expected);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> addressDAO.save(0, new Address()));
        assertThrows(IllegalArgumentException.class, () -> addressDAO.save(1, null));
        assertThrows(IllegalArgumentException.class, () -> addressDAO.save(-1, null));
    }

    @Test
    void shouldNotSaveTest() throws SQLException {
        final long receiptId = 1L;
        when(connection.prepareStatement(anyString(), anyInt())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> addressDAO.save(receiptId, expected));
    }

    @Test
    void shouldFindByIdTest() throws SQLException, DAOException {
        expected.setId(addressId);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expected.getId());
        when(resultSet.getString("country")).thenReturn(expected.getCountry());
        when(resultSet.getString("city")).thenReturn(expected.getCity());
        when(resultSet.getString("street")).thenReturn(expected.getStreet());
        when(resultSet.getString("building_number")).thenReturn(expected.getBuildingNumber());
        final Address actual = addressDAO.findById(addressId);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void shouldNotFindByIdIfInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> addressDAO.findById(0));
    }

    @Test
    void shouldNotFindById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> addressDAO.findById(addressId));
    }

    /*@Test
    void shouldFindAllTest() throws SQLException, DAOException {
        final List<Address> expectedList = List.of(
                new Address("Country1", "City1", "Street1", "Building1"),
                new Address("Country2", "City2", "Street2", "Building2"),
                new Address("Country3", "City3", "Street3", "Building3"));

        final List<Address> addresses = new ArrayList<>();
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        *//*when(resultSet.next()).thenReturn(true);*//*

       *//**//*
        int i = 0;
        for (Address address : expectedList) {
            address.setId(++i);
        }

        for (Address address : expectedList) {
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getLong("id")).thenReturn(address.getId());
            when(resultSet.getString("country")).thenReturn(address.getCountry());
            when(resultSet.getString("city")).thenReturn(address.getCity());
            when(resultSet.getString("street")).thenReturn(address.getStreet());
            when(resultSet.getString("building_number")).thenReturn(address.getBuildingNumber());
        }
        when(addressMapper.extractAddresses(addresses, preparedStatement)).thenReturn(expectedList);

        final List<Address> actual = addressDAO.findAll();
        assertEquals(expectedList, actual);
        verify(preparedStatement, times(1)).executeQuery();

    }*/

    @Test
    void shouldNotFindAll() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> addressDAO.findAll());
    }


    @Test
    void shouldUpdateTest() throws SQLException, DAOException {
        final long addressId = 1L;
        final int rowsUpdatedTrue = 4;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedTrue);
        addressDAO.update(addressId, expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertTrue(addressDAO.update(addressId, expected));
    }

    @Test
    void shouldNotUpdateWhenRowsOutOfRangeTest() throws SQLException, DAOException {
        final long addressId = 1L;
        final int rowsUpdatedFalse = 6;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedFalse);
        addressDAO.update(addressId, expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertFalse(addressDAO.update(addressId, expected));
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> addressDAO.update(0, new Address()));
        assertThrows(IllegalArgumentException.class, () -> addressDAO.update(1, null));
        assertThrows(IllegalArgumentException.class, () -> addressDAO.update(-1, null));
    }

    @Test
    void shouldNotUpdateTest() throws SQLException {
        final long addressId = 1L;
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> addressDAO.update(addressId, expected));
    }

    @Test
    void shouldUpdateByReceiptIdTest() throws SQLException, DAOException {
        final long addressId = 1L;
        final int rowsDeletedTrue = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedTrue);
        addressDAO.delete(addressId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteRowsOutOfRangeTest() throws SQLException, DAOException {
        final long addressId = 1L;
        final int rowsDeletedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedFalse);
        addressDAO.delete(addressId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> addressDAO.delete(0));
    }

    @Test
    void shouldNotDeleteTest() throws SQLException {
        final long addressId = 1L;
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> addressDAO.delete(addressId));
    }
}