package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.dao.mapper.impl.ReceiptMapper;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Contacts;
import com.october.to.finish.app.web.restaurant.model.Receipt;
import com.october.to.finish.app.web.restaurant.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Mock ReceiptMapper receiptMapper;
    @InjectMocks
    private ReceiptDAOImpl receiptDAO;
    private Receipt expected;
    private final long receiptId = 100;
    private final int offset = 4;
    private final int daoOffset = 30;

    @BeforeEach
    public void init() throws SQLException {
        User user = User.newBuilder().setId(1).build();
        Contacts contacts = new Contacts();
        contacts.setId(1);
        expected = Receipt.newBuilder()
                .setId(1)
                .setTotalPrice(200.00)
                .setCustomerId(user.getId())
                .setStatus(Receipt.Status.NEW)
                .setTimeCreated(LocalDateTime.now())
                .setContactsId(contacts.getId())
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
    void shouldNotSaveTest() throws  SQLException {
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
        when(resultSet.getBigDecimal("receipt_price")).thenReturn(BigDecimal.valueOf(expected.getTotalPrice()));
        when(resultSet.getLong("receipt_status_id")).thenReturn(expected.getStatus().getId());
        when(resultSet.getLong("contacts_id")).thenReturn(expected.getContactsId());
        when(resultSet.getLong("user_id")).thenReturn(expected.getCustomerId());
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


    /*@Test
    void shouldFindAllTest() throws SQLException, DAOException {
        final List<Receipt> expectedList = List.of(
                expected, expected, expected);
        final List<Receipt> receipts = new ArrayList<>();
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
       *//* when(resultSet.next()).thenReturn(true);*//*
        for (Receipt receipt : expectedList) {
            when(receiptMapper.extractFromResultSet(resultSet)).thenReturn(receipt);
        }
        *//*when(receiptMapper.extractReceipts(receipts, preparedStatement)).thenReturn(expectedList);*//*

        *//*when(resultSet.getLong("id")).thenReturn(expected.getId());
        when(resultSet.getTimestamp("created")).thenReturn(Timestamp.valueOf(expected.getDateCreated()));
        when(resultSet.getBigDecimal("receipt_price")).thenReturn(BigDecimal.valueOf(expected.getTotalPrice()));
        when(resultSet.getLong("user_id")).thenReturn(expected.getCustomerId());
        when(resultSet.getLong("receipt_status_id")).thenReturn(expected.getStatus().getId());
        when(resultSet.getLong("contacts_id")).thenReturn(expected.getContactsId());*//*

        *//*when(resultSet.next()).thenReturn(false);*//*


        when(receiptMapper.extractReceipts(expectedList, preparedStatement)).thenReturn(expectedList);
        final List<Receipt> actual = receiptDAO.findAll(offset);
        assertEquals(expectedList, actual);
        verify(preparedStatement, times(1)).executeQuery();
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