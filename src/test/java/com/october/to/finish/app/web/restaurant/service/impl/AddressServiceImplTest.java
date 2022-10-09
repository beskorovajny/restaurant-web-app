package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.impl.AddressDAOImpl;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Address;
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
class AddressServiceImplTest {
    @Mock
    private AddressDAOImpl addressDAO;
    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void shouldNotInjectDAO() {
        assertThrows(IllegalArgumentException.class, () -> new AddressServiceImpl(null));
    }

    @Test
    void shouldSave() throws DAOException, ServiceException {
        final long receiptId = 1L;
        final long addressId = 2L;
        final Address expected = new Address("Country", "City", "Street", "Building");
        when(addressDAO.save(receiptId, expected)).thenReturn(addressId);
        addressService.save(receiptId, expected);
        verify(addressDAO, times(1)).save(receiptId, expected);

    }

    @Test
    void shouldNotSaveIfInputIncorrect() {
        assertThrows(IllegalArgumentException.class, () -> addressService.save(0, new Address()));
        assertThrows(IllegalArgumentException.class, () -> addressService.save(1, null));
        assertThrows(IllegalArgumentException.class, () -> addressService.save(-1, null));
    }

    @Test
    void shouldNotSave() throws DAOException {
        final long receiptId = 1L;
        final Address expected = new Address("Country", "City", "Street", "Building");
        when(addressDAO.save(receiptId, expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> addressService.save(receiptId, expected));
    }

    @Test
    void shouldFindById() throws DAOException, ServiceException {
        final Address expected = new Address("Country", "City", "Street", "Building");
        expected.setId(1L);
        when(addressDAO.findById(expected.getId())).thenReturn(expected);
        final Address actual = addressService.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByIfIdIncorrect() {
        final long addressId = 0L;
        assertThrows(IllegalArgumentException.class, () -> addressService.findById(addressId));
    }

    @Test
    void shouldNotFindById() throws DAOException {
        when(addressDAO.findById(1L)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> addressService.findById(1L));
    }


    @Test
    void shouldFindAll() throws DAOException, ServiceException {
        List<Address> expected = List.of(
                new Address("Country1", "City1", "Street1", "Building1"),
                new Address("Country2", "City2", "Street2", "Building2"),
                new Address("Country3", "City3", "Street3", "Building3"));
        when(addressDAO.findAll()).thenReturn(expected);
        List<Address> actual = addressService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindAll() throws DAOException {
        when(addressDAO.findAll()).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> addressService.findAll());
    }

    @Test
    void update() throws ServiceException, DAOException {
        final long addressId = 1L;
        final Address expected = new Address("Country", "City", "Street", "Building");

        addressService.update(addressId, expected);
        verify(addressDAO, times(1)).update(addressId, expected);
    }
    @Test
    void shouldNotUpdateIfInputIncorrect() {
        assertThrows(IllegalArgumentException.class, () -> addressService.update(0, new Address()));
        assertThrows(IllegalArgumentException.class, () -> addressService.update(1, null));
        assertThrows(IllegalArgumentException.class, () -> addressService.update(-1, null));
    }

    @Test
    void shouldNotUpdate() throws DAOException {
        final long addressId = 1L;
        final Address expected = new Address("Country", "City", "Street", "Building");
        when(addressDAO.update(addressId, expected)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> addressService.update(addressId, expected));
    }

    @Test
    void shouldDelete() throws ServiceException, DAOException {
        final long addressId = 1L;

        addressService.delete(addressId);
        addressService.delete(addressId);
        verify(addressDAO, times(2)).delete(addressId);
    }

    @Test
    void shouldNotDeleteIfIdIncorrect() throws ServiceException {
        final long addressId = 0L;
        assertThrows(IllegalArgumentException.class, () -> addressService.delete(addressId));
    }

    @Test
    void shouldNotDeleteThrows() throws DAOException {
        final long addressId = 1L;
        doThrow(DAOException.class).when(addressDAO).delete(addressId);
        assertThrows(ServiceException.class, () -> addressService.delete(addressId));
    }
}