package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.impl.ContactsDAOImpl;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Contacts;
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
class ContactsServiceImplTest {
    @Mock
    private ContactsDAOImpl addressDAO;
    @InjectMocks
    private ContactsServiceImpl addressService;
    private Contacts expected;

    @BeforeEach
    void init() {
        expected = new Contacts("Country", "City", "Street", "Building", "380221111111");
    }

    @Test
    void shouldNotInjectDAOTest() {
        assertThrows(IllegalArgumentException.class, () -> new ContactsServiceImpl(null));
    }

    @Test
    void shouldSaveTest() throws DAOException, ServiceException {
        final long receiptId = 1L;
        final long addressId = 2L;
        when(addressDAO.save(receiptId, expected)).thenReturn(addressId);
        addressService.save(receiptId, expected);
        verify(addressDAO, times(1)).save(receiptId, expected);
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> addressService.save(0, new Contacts()));
        assertThrows(IllegalArgumentException.class, () -> addressService.save(1, null));
        assertThrows(IllegalArgumentException.class, () -> addressService.save(-1, null));
    }

    @Test
    void shouldNotSaveTest() throws DAOException {
        final long receiptId = 1L;
        when(addressDAO.save(receiptId, expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> addressService.save(receiptId, expected));
    }

    @Test
    void shouldFindByIdTest() throws DAOException, ServiceException {
        expected.setId(1L);
        when(addressDAO.findById(expected.getId())).thenReturn(expected);
        final Contacts actual = addressService.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIfIdIncorrectTest() {
        final long addressId = 0L;
        assertThrows(IllegalArgumentException.class, () -> addressService.findById(addressId));
    }

    @Test
    void shouldNotFindByIdTest() throws DAOException {
        when(addressDAO.findById(1L)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> addressService.findById(1L));
    }


    @Test
    void shouldFindAllTest() throws DAOException, ServiceException {
        List<Contacts> expected = List.of(
                new Contacts("Country1", "City1", "Street1", "Building1", "380666666665"),
                new Contacts("Country2", "City2", "Street2", "Building2", "380666666666"),
                new Contacts("Country3", "City3", "Street3", "Building3", "380666666667"));
        when(addressDAO.findAll()).thenReturn(expected);
        List<Contacts> actual = addressService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindAllTest() throws DAOException {
        when(addressDAO.findAll()).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> addressService.findAll());
    }

    @Test
    void shouldUpdateTest() throws ServiceException, DAOException {
        final long addressId = 1L;
        addressService.update(addressId, expected);
        verify(addressDAO, times(1)).update(addressId, expected);
    }
    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> addressService.update(0, new Contacts()));
        assertThrows(IllegalArgumentException.class, () -> addressService.update(1, null));
        assertThrows(IllegalArgumentException.class, () -> addressService.update(-1, null));
    }

    @Test
    void shouldNotUpdateTest() throws DAOException {
        final long addressId = 1L;
        when(addressDAO.update(addressId, expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> addressService.update(addressId, expected));
    }

    @Test
    void shouldDeleteTest() throws ServiceException, DAOException {
        final long addressId = 1L;

        addressService.delete(addressId);
        addressService.delete(addressId);
        verify(addressDAO, times(2)).delete(addressId);
    }

    @Test
    void shouldNotDeleteIfIdIncorrectTest() throws ServiceException {
        final long addressId = 0L;
        assertThrows(IllegalArgumentException.class, () -> addressService.delete(addressId));
    }

    @Test
    void shouldNotDeleteThrowsTest() throws DAOException {
        final long addressId = 1L;
        doThrow(DAOException.class).when(addressDAO).delete(addressId);
        assertThrows(ServiceException.class, () -> addressService.delete(addressId));
    }
}