package com.october.to.finish.app.web.restaurant.dao.impl;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.model.Dish;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishDAOImplTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private DishDAOImpl dishDAO;
    private Dish expected;

    private final String title = "title";
    private final long dishId = 100;
    private final int offset = 4;

    @BeforeEach
    public void setUp() {
        expected = Dish.newBuilder().setId(1)
                .setTitle("Pizza")
                .setDescription("description")
                .setDateCreated(LocalDateTime.now())
                .setCooking(25)
                .setCategory(Dish.Category.PIZZA)
                .setWeight(425)
                .setPrice(200).build();
    }

    @AfterEach
    public void tearDown() {
        expected = null;
    }

    @Test
    void shouldNotInjectTest() {
        assertThrows(IllegalArgumentException.class, () -> new DishDAOImpl(null));
    }

    @Test
    void getConnectionTest() {
        assertEquals(connection, dishDAO.getConnection());
    }

    @Test
    void shouldSaveTest() throws SQLException, DAOException {

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(anyInt())).thenReturn(1L);
        dishDAO.save(expected);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> dishDAO.save(null));
    }

    @Test
    void shouldNotSaveTest() throws SQLException {
        when(connection.prepareStatement(anyString(), anyInt())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> dishDAO.save(expected));
    }

    @Test
    void shouldFindByIdTest() throws SQLException, DAOException {
        expected.setId(dishId);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expected.getId());
        when(resultSet.getString("title")).thenReturn(expected.getTitle());
        when(resultSet.getString("description")).thenReturn(expected.getDescription());
        when(resultSet.getBigDecimal("price")).thenReturn(BigDecimal.valueOf(expected.getPrice()));
        when(resultSet.getInt("weight")).thenReturn(expected.getWeight());
        when(resultSet.getInt("cooking")).thenReturn(expected.getCooking());
        when(resultSet.getTimestamp("created")).thenReturn(Timestamp.valueOf(expected.getDateCreated()));
        when(resultSet.getLong("category_id")).thenReturn(expected.getCategory().getId());
        final Dish actual = dishDAO.findById(dishId);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void shouldNotFindByIdIfInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> dishDAO.findById(0));
    }

    @Test
    void shouldNotFindById() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> dishDAO.findById(dishId));
    }

    @Test
    void shouldFindByTitleTest() throws SQLException, DAOException {

        expected.setId(dishId);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expected.getId());
        when(resultSet.getString("title")).thenReturn(expected.getTitle());
        when(resultSet.getString("description")).thenReturn(expected.getDescription());
        when(resultSet.getBigDecimal("price")).thenReturn(BigDecimal.valueOf(expected.getPrice()));
        when(resultSet.getInt("weight")).thenReturn(expected.getWeight());
        when(resultSet.getInt("cooking")).thenReturn(expected.getCooking());
        when(resultSet.getTimestamp("created")).thenReturn(Timestamp.valueOf(expected.getDateCreated()));
        when(resultSet.getLong("category_id")).thenReturn(expected.getCategory().getId());
        final Dish actual = dishDAO.findByTitle(title);
        assertEquals(expected, actual);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void shouldNotFindByTitleIfInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> dishDAO.findByTitle(null));
        assertThrows(IllegalArgumentException.class, () -> dishDAO.findByTitle(""));
    }

    @Test
    void shouldNotFindByTitle() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> dishDAO.findByTitle(title));
    }

    @Test
    void shouldNotFindAll() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> dishDAO.findAll(offset));
    }


    @Test
    void shouldUpdateTest() throws SQLException, DAOException {
        final int rowsUpdatedTrue = 7;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedTrue);
        dishDAO.update(dishId, expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertTrue(dishDAO.update(dishId, expected));
    }

    @Test
    void shouldNotUpdateWhenRowsOutOfRangeTest() throws SQLException, DAOException {
        final int rowsUpdatedFalse = 9;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsUpdatedFalse);
        dishDAO.update(dishId, expected);
        verify(preparedStatement, times(1)).executeUpdate();
        assertFalse(dishDAO.update(dishId, expected));
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> dishDAO.update(0, expected));
        assertThrows(IllegalArgumentException.class, () -> dishDAO.update(1, null));
        assertThrows(IllegalArgumentException.class, () -> dishDAO.update(-1, null));
    }

    @Test
    void shouldNotUpdateTest() throws DAOException, SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> dishDAO.update(dishId, expected));
    }

    @Test
    void shouldUpdateByReceiptIdTest() throws SQLException, DAOException {
        final int rowsDeletedTrue = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedTrue);
        dishDAO.delete(dishId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteRowsOutOfRangeTest() throws SQLException, DAOException {
        final int rowsDeletedFalse = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(rowsDeletedFalse);
        dishDAO.delete(dishId);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldNotDeleteIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> dishDAO.delete(0));
    }

    @Test
    void shouldNotDeleteTest() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        assertThrows(DAOException.class, () -> dishDAO.delete(dishId));
    }
}