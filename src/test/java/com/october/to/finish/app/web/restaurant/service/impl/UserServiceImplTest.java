package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.impl.UserDAOImpl;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.security.PasswordEncryptionUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserDAOImpl userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    private User expected;
    private final String password = "pa88w0rd";
    private int offset;

    @BeforeEach
    public void init() {
        offset = 3;
        expected = User.newBuilder()
                .setEmail("test@examp.com")
                .setFirstName("fTest")
                .setLastName("lTest")
                .setPhoneNumber("000-000-0000")
                .setRole(User.Role.CLIENT)
                .setPassword(PasswordEncryptionUtil.getEncrypted(password).toCharArray()).build();

    }

    @AfterEach
    void teardown() {
        expected = null;
    }

    @Test
    void shouldGetRecordsCount() {
        final int records = 5;
        when(userDAO.countRecords()).thenReturn(records);
        assertEquals(records, userService.getRecordsCount());
    }

    @Test
    void shouldNotInjectDAO() {
        assertThrows(IllegalArgumentException.class, () -> new UserServiceImpl(null));
    }

    @Test
    void shouldSave() throws DAOException, ServiceException {
        final long userId = 1L;
        when(userDAO.save(expected)).thenReturn(userId);
        userService.save(expected);
        userService.save(expected);
        verify(userDAO, times(2)).save(expected);
    }

    @Test
    void shouldNotSaveIfInputIncorrect() {
        assertThrows(IllegalArgumentException.class, () -> userService.save(null));
    }

    @Test
    void shouldNotSave() throws DAOException {
        when(userDAO.save(expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> userService.save(expected));
    }

    @Test
    void shouldFindById() throws DAOException, ServiceException {
        final long userId = 1L;
        expected.setId(userId);
        when(userDAO.findById(expected.getId())).thenReturn(expected);
        final User actual = userService.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIfIdIncorrect() {
        final long userId = 0L;
        assertThrows(IllegalArgumentException.class, () -> userService.findById(userId));
    }

    @Test
    void shouldNotFindById() throws DAOException {
        final long userId = 1L;
        when(userDAO.findById(userId)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> userService.findById(userId));
    }

    @Test
    void shouldFindByEmail() throws DAOException, ServiceException {
        final String email = "test1@examp.app";
        when(userDAO.findByEmail(email)).thenReturn(expected);
        final User actual = userService.findByEmail(email);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIfEmailIncorrect() {
        assertThrows(IllegalArgumentException.class, () -> userService.findByEmail(null));
    }

    @Test
    void shouldNotFindByEmail() throws DAOException {
        final String email = "test1@example.app";
        when(userDAO.findByEmail(email)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> userService.findByEmail(email));
    }


    @Test
    void shouldFindAll() throws DAOException, ServiceException {
        List<User> expectedList = List.of(
                User.newBuilder()
                        .setEmail("test@examp.com")
                        .setFirstName("fTest")
                        .setLastName("lTest")
                        .setPhoneNumber("000-000-0000")
                        .setRole(User.Role.CLIENT)
                        .setPassword(PasswordEncryptionUtil.getEncrypted(password).toCharArray()).build(),
                User.newBuilder()
                        .setEmail("test1@examp.com")
                        .setFirstName("fTest1")
                        .setLastName("lTest1")
                        .setPhoneNumber("000-000-0001")
                        .setRole(User.Role.MANAGER)
                        .setPassword(PasswordEncryptionUtil.getEncrypted(password).toCharArray()).build(),
                User.newBuilder()
                        .setEmail("test2@examp.com")
                        .setFirstName("fTest2")
                        .setLastName("lTest2")
                        .setPhoneNumber("000-000-0002")
                        .setRole(User.Role.CLIENT)
                        .setPassword(PasswordEncryptionUtil.getEncrypted(password).toCharArray()).build());
        final int daoOffset = 20;
        when(userDAO.findAll(daoOffset)).thenReturn(expectedList);
        List<User> actual = userService.findAll(offset);
        assertEquals(expectedList, actual);
    }

    @Test
    void shouldNotFindAll() throws DAOException {
        final int daoOffset = 20;
        when(userDAO.findAll(daoOffset)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> userService.findAll(offset));
    }

    @Test
    void update() throws ServiceException, DAOException {
        final long userId = 1L;

        userService.update(userId, expected);
        userService.update(userId, expected);
        userService.update(userId, expected);
        verify(userDAO, times(3)).update(userId, expected);
    }

    @Test
    void shouldNotUpdateIfInputIncorrect() {
        assertThrows(IllegalArgumentException.class, () -> userService.update(0, new User()));
        assertThrows(IllegalArgumentException.class, () -> userService.update(1, null));
        assertThrows(IllegalArgumentException.class, () -> userService.update(-1, null));
    }

    @Test
    void shouldNotUpdate() throws DAOException {
        final long userId = 1L;
        when(userDAO.update(userId, expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> userService.update(userId, expected));
    }

    @Test
    void shouldDelete() throws ServiceException, DAOException {
        final long userId = 1L;

        userService.delete(userId);
        userService.delete(userId);
        verify(userDAO, times(2)).delete(userId);
    }

    @Test
    void shouldNotDeleteIfIdIncorrect() throws ServiceException {
        final long userId = 0L;
        assertThrows(IllegalArgumentException.class, () -> userService.delete(userId));
    }

    @Test
    void shouldNotDeleteThrows() throws DAOException {
        final long userId = 1L;
        doThrow(DAOException.class).when(userDAO).delete(userId);
        assertThrows(ServiceException.class, () -> userService.delete(userId));
    }
}