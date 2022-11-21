package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.security.PasswordEncryptionUtil;
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
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDAOImplTest {
    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    ResultSet resultSet;
    @InjectMocks
    private UserDAOImpl userDAO;
    private User expected;

    private final String eMail = "title";
    private final long userId = 100;
    private final int offset = 4;

    @BeforeEach
    public void setUp() {
        String password = "pa88w0rd";
        expected = User.newBuilder()
                .setEmail("test@examp.com")
                .setFirstName("fTest")
                .setLastName("lTest")
                .setRole(User.Role.CLIENT)
                .setPassword(PasswordEncryptionUtil.getEncrypted(password).toCharArray())
                .build();
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    void shouldNotInjectTest() {
        assertThrows(IllegalArgumentException.class, () -> new UserDAOImpl(null));
    }

    @Test
    void getConnectionTest() {
        assertEquals(connection, userDAO.getConnection());
    }

    @Test
    void shouldSaveTest() throws SQLException, DAOException {

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(anyInt())).thenReturn(1L);
        userDAO.save(expected);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> userDAO.save(null));
    }

    @Test
    void shouldNotSaveTest() throws SQLException {
        when(connection.prepareStatement(anyString(), anyInt())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.save(expected));
    }

    @Test
    void shouldFindByIdTest() throws SQLException, DAOException {
        expected.setId(userId);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expected.getId());
        when(resultSet.getString("email")).thenReturn(expected.getEmail());
        when(resultSet.getString("first_name")).thenReturn(expected.getFirstName());
        when(resultSet.getString("last_name")).thenReturn(expected.getLastName());
        when(resultSet.getLong("role_id")).thenReturn((long) expected.getRole().getId());
        when(resultSet.getString("password")).thenReturn(String.valueOf(expected.getPassword()));
        expected.setOrders(new HashSet<>());
        final User actual = userDAO.findById(userId);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void shouldNotFindByIdIfInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> userDAO.findById(0));
    }

    @Test
    void shouldNotFindById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.findById(userId));
    }

    @Test
    void shouldFindByEmailTest() throws SQLException, DAOException {
        expected.setId(userId);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expected.getId());
        when(resultSet.getString("email")).thenReturn(expected.getEmail());
        when(resultSet.getString("first_name")).thenReturn(expected.getFirstName());
        when(resultSet.getString("last_name")).thenReturn(expected.getLastName());
        when(resultSet.getLong("role_id")).thenReturn((long) expected.getRole().getId());
        when(resultSet.getString("password")).thenReturn(String.valueOf(expected.getPassword()));
        expected.setOrders(new HashSet<>());
        final User actual = userDAO.findByEmail(eMail);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void shouldNotFindByTitleIfInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> userDAO.findByEmail(null));
        assertThrows(IllegalArgumentException.class, () -> userDAO.findByEmail(""));
    }

    @Test
    void shouldNotFindByTitle() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.findByEmail(eMail));
    }

    @Test
    void shouldNotFindAll() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.findAll(offset));
    }


    @Test
    void shouldUpdateTest() throws SQLException, DAOException {
        final int rowsUpdatedTrue = 5;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedTrue);
        userDAO.update(userId, expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertTrue(userDAO.update(userId, expected));
    }

    @Test
    void shouldNotUpdateWhenRowsOutOfRangeTest() throws SQLException, DAOException {
        final int rowsUpdatedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedFalse);
        userDAO.update(userId, expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertFalse(userDAO.update(userId, expected));
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> userDAO.update(0, expected));
        assertThrows(IllegalArgumentException.class, () -> userDAO.update(1, null));
        assertThrows(IllegalArgumentException.class, () -> userDAO.update(-1, null));
    }

    @Test
    void shouldNotUpdateTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.update(userId, expected));
    }

    @Test
    void shouldUpdateByReceiptIdTest() throws SQLException, DAOException {
        final int rowsDeletedTrue = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedTrue);
        userDAO.delete(userId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteRowsOutOfRangeTest() throws SQLException, DAOException {
        final int rowsDeletedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedFalse);
        userDAO.delete(userId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> userDAO.delete(0));
    }

    @Test
    void shouldNotDeleteTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> userDAO.delete(userId));
    }
}