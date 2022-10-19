package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.dao.mapper.impl.ContactsMapper;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Contacts;
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
class ContactsDAOImplTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private ContactsMapper contactsMapper;
    @InjectMocks
    private ContactsDAOImpl contactsDAO;
    private Contacts expected;
    private final long addressId = 100;

    @BeforeEach
    public void setUp() {
        expected = new Contacts("Country", "City", "Street", "Building", "380xx-xxx-xx-xx");
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    void shouldNotInjectTest() {
        assertThrows(IllegalArgumentException.class, () -> new ContactsDAOImpl(null));
    }

    @Test
    void getConnectionTest() {
        assertEquals(connection, contactsDAO.getConnection());
    }

    @Test
    void shouldSaveTest() throws SQLException, DAOException {
        final long receiptId = 1L;

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(anyInt())).thenReturn(1L);
        contactsDAO.save(expected);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> contactsDAO.save( null));
    }

    @Test
    void shouldNotSaveTest() throws SQLException {
        when(connection.prepareStatement(anyString(), anyInt())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> contactsDAO.save(expected));
    }

    @Test
    void shouldFindByIdTest() throws SQLException, DAOException {
        expected.setId(addressId);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expected.getId());
        when(resultSet.getString("country")).thenReturn(expected.getCountry());
        when(resultSet.getString("phone")).thenReturn(expected.getPhone());
        when(resultSet.getString("city")).thenReturn(expected.getCity());
        when(resultSet.getString("street")).thenReturn(expected.getStreet());
        when(resultSet.getString("building")).thenReturn(expected.getBuildingNumber());
        final Contacts actual = contactsDAO.findById(addressId);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void shouldNotFindByIdIfInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> contactsDAO.findById(0));
    }

    @Test
    void shouldNotFindById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> contactsDAO.findById(addressId));
    }

    /*@Test
    void shouldFindAllTest() throws SQLException, DAOException {
        final List<Contacts> expectedList = List.of(
                new Contacts("Country1", "City1", "Street1", "Building1"),
                new Contacts("Country2", "City2", "Street2", "Building2"),
                new Contacts("Country3", "City3", "Street3", "Building3"));

        final List<Contacts> addresses = new ArrayList<>();
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        *//*when(resultSet.next()).thenReturn(true);*//*

       *//**//*
        int i = 0;
        for (Contacts address : expectedList) {
            address.setId(++i);
        }

        for (Contacts address : expectedList) {
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getLong("id")).thenReturn(address.getId());
            when(resultSet.getString("country")).thenReturn(address.getCountry());
            when(resultSet.getString("city")).thenReturn(address.getCity());
            when(resultSet.getString("street")).thenReturn(address.getStreet());
            when(resultSet.getString("building_number")).thenReturn(address.getBuildingNumber());
        }
        when(contactsMapper.extractAddresses(addresses, preparedStatement)).thenReturn(expectedList);

        final List<Contacts> actual = contactsDAO.findAll();
        assertEquals(expectedList, actual);
        verify(preparedStatement, times(1)).executeQuery();

    }*/

    @Test
    void shouldNotFindAll() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> contactsDAO.findAll());
    }


    @Test
    void shouldUpdateTest() throws SQLException, DAOException {
        final long addressId = 1L;
        final int rowsUpdatedTrue = 4;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedTrue);
        contactsDAO.update(addressId, expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertTrue(contactsDAO.update(addressId, expected));
    }

    @Test
    void shouldNotUpdateWhenRowsOutOfRangeTest() throws SQLException, DAOException {
        final long addressId = 1L;
        final int rowsUpdatedFalse = 6;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedFalse);
        contactsDAO.update(addressId, expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertFalse(contactsDAO.update(addressId, expected));
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> contactsDAO.update(0, new Contacts()));
        assertThrows(IllegalArgumentException.class, () -> contactsDAO.update(1, null));
        assertThrows(IllegalArgumentException.class, () -> contactsDAO.update(-1, null));
    }

    @Test
    void shouldNotUpdateTest() throws SQLException {
        final long addressId = 1L;
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> contactsDAO.update(addressId, expected));
    }

    @Test
    void shouldUpdateByReceiptIdTest() throws SQLException, DAOException {
        final long addressId = 1L;
        final int rowsDeletedTrue = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedTrue);
        contactsDAO.delete(addressId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteRowsOutOfRangeTest() throws SQLException, DAOException {
        final long addressId = 1L;
        final int rowsDeletedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedFalse);
        contactsDAO.delete(addressId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> contactsDAO.delete(0));
    }

    @Test
    void shouldNotDeleteTest() throws SQLException {
        final long addressId = 1L;
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> contactsDAO.delete(addressId));
    }
}