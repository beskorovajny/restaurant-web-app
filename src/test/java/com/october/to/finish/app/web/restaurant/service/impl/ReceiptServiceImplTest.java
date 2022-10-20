package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.DishDAO;
import com.october.to.finish.app.web.restaurant.dao.impl.ContactsDAOImpl;
import com.october.to.finish.app.web.restaurant.dao.impl.DishDAOImpl;
import com.october.to.finish.app.web.restaurant.dao.impl.ReceiptDAOImpl;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.model.Receipt;
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
class ReceiptServiceImplTest {

    @Mock
    private ReceiptDAOImpl receiptDAO;
    @Mock
    private ContactsDAOImpl contactsDAO;

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    private Receipt expectedReceipt;

    private final int offset = 3;
    private final long receiptId = 1L;
    private final long userId = 5L;

    @BeforeEach
    void setUp() {
        expectedReceipt = Receipt.newBuilder()
                .setId(1)
                .setTotalPrice(200.00)
                .setCustomerId(1)
                .setStatus(Receipt.Status.NEW)
                .setTimeCreated(LocalDateTime.now())
                .setContactsId(1)
                .build();
    }

    @AfterEach
    void tearDown() {
        expectedReceipt = null;
    }

    @Test
    void shouldGetRecordsCountTest() throws DAOException {
        final int records = 5;
        when(receiptDAO.countRecords()).thenReturn(records);
        assertEquals(records, receiptService.getRecordsCount());
    }


    @Test
    void shouldNotInjectDAOTest() {
        assertThrows(IllegalArgumentException.class, () -> new ReceiptServiceImpl(null, null));
    }

    @Test
    void shouldSaveTest() throws DAOException, ServiceException {
        when(receiptDAO.save(userId, expectedReceipt)).thenReturn(receiptId);
        receiptService.save(userId, expectedReceipt);
        receiptService.save(userId, expectedReceipt);
        verify(receiptDAO, times(2)).save(userId, expectedReceipt);
    }

    @Test
    void shouldNotSaveIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> receiptService.save(0, null));
        assertThrows(IllegalArgumentException.class, () -> receiptService.save(1,null));
        assertThrows(IllegalArgumentException.class, () -> receiptService.save(-1, expectedReceipt));
    }

    @Test
    void shouldNotSaveTest() throws DAOException {
        when(receiptDAO.save(userId, expectedReceipt)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> receiptService.save(userId, expectedReceipt));
    }

    @Test
    void shouldFindByIdTest() throws DAOException, ServiceException {
        expectedReceipt.setId(receiptId);
        when(receiptService.findById(receiptId)).thenReturn(expectedReceipt);
        final Receipt actual = receiptService.findById(receiptId);
        assertEquals(expectedReceipt, actual);
    }

    @Test
    void shouldNotFindByIfIdIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> receiptService.findById(-1));
    }

    @Test
    void shouldNotFindByIdTest() throws DAOException {
        when(receiptDAO.findById(receiptId)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> receiptService.findById(receiptId));
    }

    @Test
    void shouldFindAllTest() throws DAOException, ServiceException {
        List<Receipt> expectedList = List.of(expectedReceipt, expectedReceipt, expectedReceipt);
        final int daoOffset = 20;
        when(receiptDAO.findAll(daoOffset)).thenReturn(expectedList);
        List<Receipt> actual = receiptService.findAll(offset);
        assertEquals(expectedList, actual);
    }

    @Test
    void shouldNotFindAllTest() throws DAOException {
        final int daoOffset = 20;
        when(receiptDAO.findAll(daoOffset)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> receiptService.findAll(offset));
    }

    @Test
    void shouldUpdateTest() throws ServiceException, DAOException {
        receiptService.update(receiptId, expectedReceipt);
        receiptService.update(receiptId, expectedReceipt);
        receiptService.update(receiptId, expectedReceipt);
        verify(receiptDAO, times(3)).update(receiptId, expectedReceipt);
    }

    @Test
    void shouldNotUpdateIfInputIncorrectTest() {
        assertThrows(IllegalArgumentException.class, () -> receiptService.update(0, new Receipt()));
        assertThrows(IllegalArgumentException.class, () -> receiptService.update(1, null));
        assertThrows(IllegalArgumentException.class, () -> receiptService.update(-1, null));
    }

    @Test
    void shouldNotUpdateTest() throws DAOException {
        when(receiptDAO.update(receiptId, expectedReceipt)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> receiptService.update(receiptId, expectedReceipt));
    }

    @Test
    void shouldDeleteTest() throws ServiceException, DAOException {
        receiptService.delete(receiptId);
        receiptService.delete(receiptId);
        verify(receiptDAO, times(2)).delete(receiptId);
    }

    @Test
    void shouldNotDeleteIfIdIncorrectTest() throws ServiceException {
        assertThrows(IllegalArgumentException.class, () -> receiptService.delete(0));
    }

    @Test
    void shouldNotDeleteThrowsTest() throws DAOException {
        doThrow(DAOException.class).when(receiptDAO).delete(receiptId);
        assertThrows(ServiceException.class, () -> receiptService.delete(receiptId));
    }
}