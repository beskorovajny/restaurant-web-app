package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Address;
import com.october.to.finish.app.web.restaurant.model.Receipt;
import com.october.to.finish.app.web.restaurant.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptDAOImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private ReceiptDAOImpl receiptDAO;
    private Receipt expected;
    private final long receiptId = 100;

    private final int offset = 4;

    @BeforeEach
    public void setUp() throws SQLException {
        User user = User.newBuilder().setId(1).build();
        Address address = new Address();
        address.setId(1);
        expected = Receipt.newBuilder()
                .setId(1)
                .setCustomer(user)
                .setStatus(Receipt.Status.NEW)
                .setTimeCreated(LocalDateTime.now())
                .setDiscount(75)
                .setAddress(address)
                .build();
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    void shouldNotInjectTest() {
        assertThrows(IllegalArgumentException.class, () -> new ReceiptDAOImpl(null));
    }

    @Test
    void getConnectionTest() {
        assertEquals(connection, receiptDAO.getConnection());
    }

    @Test
    void shouldSaveTest() throws SQLException, DAOException {
        final long userId = 1L;

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(anyInt())).thenReturn(1L);
        receiptDAO.save(userId, expected);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> receiptDAO.save(0, new Receipt()));
        assertThrows(IllegalArgumentException.class, () -> receiptDAO.save(1, null));
        assertThrows(IllegalArgumentException.class, () -> receiptDAO.save(-1, null));
    }

    @Test
    void shouldNotSaveTest() throws DAOException, SQLException {
        final long userId = 1L;
        when(connection.prepareStatement(anyString(), anyInt())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> receiptDAO.save(userId, expected));
    }

    @Test
    void shouldFindByIdTest() throws SQLException, DAOException {
        expected.setId(receiptId);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expected.getId());
        when(resultSet.getTimestamp("created")).thenReturn(Timestamp.valueOf(expected.getDateCreated()));
        when(resultSet.getInt("discount")).thenReturn(expected.getDiscount());
        when(resultSet.getLong("receipt_status_id")).thenReturn(expected.getStatus().getId());
        when(resultSet.getLong("address_id")).thenReturn(expected.getAddress().getId());
        when(resultSet.getLong("user_id")).thenReturn(expected.getCustomer().getId());
        final Receipt actual = receiptDAO.findById(receiptId);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void shouldNotFindByIdIfInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> receiptDAO.findById(0));
    }

    @Test
    void shouldNotFindById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> receiptDAO.findById(receiptId));
    }

   /* @Test
    void shouldFindAllTest() throws SQLException, DAOException {
       *//* final List<Address> expectedList = List.of(
                new Address("Country1", "City1", "Street1", "Building1"),
                new Address("Country2", "City2", "Street2", "Building2"),
                new Address("Country3", "City3", "Street3", "Building3"));
        final List<Address> addresses = new ArrayList<>();
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expected.getId());
        when(resultSet.getString("country")).thenReturn(expected.getCountry());
        when(resultSet.getString("city")).thenReturn(expected.getCity());
        when(resultSet.getString("street")).thenReturn(expected.getStreet());
        when(resultSet.getString("building_number")).thenReturn(expected.getBuildingNumber());
        when(addressMapper.extractAddresses(addresses, preparedStatement)).thenReturn(expectedList);
        final List<Address> actual = dishDAO.findAll();
        assertEquals(expectedList, actual);
        verify(preparedStatement, times(1)).executeQuery();
*//*
    }*/

    @Test
    void shouldNotFindAll() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> receiptDAO.findAll(offset));
    }


    @Test
    void shouldUpdateTest() throws SQLException, DAOException {
        final int rowsUpdatedTrue = 5;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedTrue);
        receiptDAO.update(receiptId, expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertTrue(receiptDAO.update(receiptId, expected));
    }

    @Test
    void shouldNotUpdateWhenRowsOutOfRangeTest() throws SQLException, DAOException {
        final int rowsUpdatedFalse = 6;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedFalse);
        receiptDAO.update(receiptId, expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertFalse(receiptDAO.update(receiptId, expected));
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> receiptDAO.update(0, expected));
        assertThrows(IllegalArgumentException.class, () -> receiptDAO.update(1, null));
        assertThrows(IllegalArgumentException.class, () -> receiptDAO.update(-1, null));
    }

    @Test
    void shouldNotUpdateTest() throws DAOException, SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> receiptDAO.update(receiptId, expected));
    }

    @Test
    void shouldUpdateByReceiptIdTest() throws SQLException, DAOException {
        final int rowsDeletedTrue = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedTrue);
        receiptDAO.delete(receiptId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteRowsOutOfRangeTest() throws SQLException, DAOException {
        final int rowsDeletedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedFalse);
        receiptDAO.delete(receiptId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> receiptDAO.delete(0));
    }

    @Test
    void shouldNotDeleteTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> receiptDAO.delete(receiptId));
    }
}