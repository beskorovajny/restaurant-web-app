package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.dao.mapper.impl.AddressMapper;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressDAOImplTest {
    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    ResultSet resultSet;
    @Mock
    AddressMapper addressMapper = new AddressMapper();
    @InjectMocks
    AddressDAOImpl addressDAO;
    private Address expected;
    private long addressId = 100;

    @BeforeEach
    public void setUp() throws SQLException {
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

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
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
    void shouldNotSaveTest() throws DAOException, SQLException {
        final long addressId = 1L;
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> addressDAO.save(addressId, expected));
    }

    @Test
    void shouldFindByIdTest() {
    }

    @Test
    void shouldFindAllTest() {
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
    void shouldNotUpdateTest() throws DAOException, SQLException {
        final long addressId = 1L;
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> addressDAO.update(addressId, expected));
    }

    @Test
    void shouldUpdateByReceiptIdTest() throws SQLException, DAOException {
        final long addressId = 1L;
        final int rowsDeletedFalse = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedFalse);
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