package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.impl.DishDAOImpl;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Dish;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishServiceImplTest {

    @Mock
    private DishDAOImpl dishDAO;
    @InjectMocks
    private DishServiceImpl dishService;

    private Dish expected;

    private int offset;

    @BeforeEach
    public void setUp() {
        offset = 3;
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
    void teardown() {
        expected = null;
    }

    @Test
    void shouldGetRecordsCountTest() {
        final int records = 5;
        when(dishDAO.countRecords()).thenReturn(records);
        assertEquals(records, dishService.getRecordsCount());
    }

    @Test
    void shouldNotInjectDAOTest() {
        assertThrows(IllegalArgumentException.class, () -> new DishServiceImpl(null));
    }

    @Test
    void shouldSaveTest() throws DAOException, ServiceException {
        final long dishId = 1L;
        when(dishDAO.save(expected)).thenReturn(dishId);
        dishService.save(expected);
        dishService.save(expected);
        verify(dishDAO, times(2)).save(expected);
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> dishService.save(null));
    }

    @Test
    void shouldNotSaveTest() throws DAOException {
        when(dishDAO.save(expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> dishService.save(expected));
    }

    @Test
    void shouldFindByIdTest() throws DAOException, ServiceException {
        final long dishId = 1L;
        expected.setId(dishId);
        when(dishDAO.findById(expected.getId())).thenReturn(expected);
        final Dish actual = dishService.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIfIdIncorrectTest() {
        final long dishId = 0L;
        assertThrows(IllegalArgumentException.class, () -> dishService.findById(dishId));
    }

    @Test
    void shouldNotFindByIdTest() throws DAOException {
        final long dishId = 1L;
        when(dishDAO.findById(dishId)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> dishService.findById(dishId));
    }

    @Test
    void shouldFindByTitleTest() throws DAOException, ServiceException {
        final String title = "Title";
        when(dishDAO.findByTitle(title)).thenReturn(expected);
        final Dish actual = dishService.findByTitle(title);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIfTitleIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> dishService.findByTitle(null));
    }

    @Test
    void shouldNotFindByTitleTest() throws DAOException {
        final String title = "title1";
        when(dishDAO.findByTitle(title)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> dishService.findByTitle(title));
    }


    @Test
    void shouldFindAllTest() throws DAOException, ServiceException {
        List<Dish> expectedList = List.of(
                Dish.newBuilder().setId(1)
                        .setTitle("Pizza")
                        .setDescription("description")
                        .setDateCreated(LocalDateTime.now())
                        .setCooking(25)
                        .setCategory(Dish.Category.PIZZA)
                        .setWeight(425)
                        .setPrice(200).build(),
                Dish.newBuilder().setId(2)
                        .setTitle("Salad")
                        .setDescription("description")
                        .setDateCreated(LocalDateTime.now())
                        .setCooking(5)
                        .setCategory(Dish.Category.SALAD)
                        .setWeight(225)
                        .setPrice(100).build(),
                Dish.newBuilder().setId(3)
                        .setTitle("Pizza2")
                        .setDescription("description")
                        .setDateCreated(LocalDateTime.now())
                        .setCooking(30)
                        .setCategory(Dish.Category.PIZZA)
                        .setWeight(625)
                        .setPrice(300).build());
        final int daoOffset = 20;
        when(dishDAO.findAll(daoOffset)).thenReturn(expectedList);
        List<Dish> actual = dishService.findAll(offset);
        assertEquals(expectedList, actual);
    }

    @Test
    void shouldNotFindAllTest() throws DAOException {
        final int daoOffset = 20;
        when(dishDAO.findAll(daoOffset)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> dishService.findAll(offset));
    }

    @Test
    void shouldUpdateTest() throws ServiceException, DAOException {
        final long dishId = 1L;

        dishService.update(dishId, expected);
        dishService.update(dishId, expected);
        dishService.update(dishId, expected);
        verify(dishDAO, times(3)).update(dishId, expected);
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> dishService.update(0, new Dish()));
        assertThrows(IllegalArgumentException.class, () -> dishService.update(1, null));
        assertThrows(IllegalArgumentException.class, () -> dishService.update(-1, null));
    }

    @Test
    void shouldNotUpdateTest() throws DAOException {
        final long dishId = 1L;
        when(dishDAO.update(dishId, expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> dishService.update(dishId, expected));
    }

    @Test
    void shouldDeleteTest() throws ServiceException, DAOException {
        final long dishId = 1L;

        dishService.delete(dishId);
        dishService.delete(dishId);
        verify(dishDAO, times(2)).delete(dishId);
    }

    @Test
    void shouldNotDeleteIfIdIncorrectTest() throws ServiceException {
        final long dishId = 0L;
        assertThrows(IllegalArgumentException.class, () -> dishService.delete(dishId));
    }

    @Test
    void shouldNotDeleteThrowsTest() throws DAOException {
        final long dishId = 1L;
        doThrow(DAOException.class).when(dishDAO).delete(dishId);
        assertThrows(ServiceException.class, () -> dishService.delete(dishId));
    }

}