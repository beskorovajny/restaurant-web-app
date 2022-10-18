package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.ContactsDAO;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Contacts;
import com.october.to.finish.app.web.restaurant.service.ContactsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ContactsServiceImpl implements ContactsService {
    private static final Logger LOGGER = LogManager.getLogger(ContactsServiceImpl.class);
    private static final String NULL_ADDRESS_DAO_EXC = "[ContactsService] Can't create ContactsService with null input ContactsDAO";
    private static final String NULL_ADDRESS_INPUT_EXC = "[ContactsService] Can't operate null (or < 1) input!";
    private static final String EXISTED_ADDRESS_EXC =
            "[ContactsService] Contacts with given [ID: {}] is already existed!";
    private final ContactsDAO contactsDAO;

    public ContactsServiceImpl(ContactsDAO contactsDAO) {
        if (contactsDAO == null) {
            LOGGER.error(NULL_ADDRESS_DAO_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_DAO_EXC);
        }
        this.contactsDAO = contactsDAO;
    }

    @Override
    public void save(Contacts contacts) throws ServiceException {
        if (contacts == null) {
            LOGGER.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            contacts.setId(contactsDAO.save(contacts));
            LOGGER.info("[ContactsService] Contacts saved. (id: {})", contacts.getId());
        } catch (DAOException e) {
            LOGGER.error("[ContactsService] SQLException while saving Contacts; Exc: {}"
                    , e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Contacts findById(long addressId) throws ServiceException {
        if (addressId < 1) {
            LOGGER.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            return contactsDAO.findById(addressId);
        } catch (DAOException e) {
            LOGGER.error("[ContactsService] An exception occurs while receiving Contacts. (id: {}). Exc: {}"
                    , addressId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Contacts> findAll() throws ServiceException {
        try {
            return contactsDAO.findAll();
        } catch (DAOException e) {
            LOGGER.error("[ContactsService] An exception occurs while receiving Addresses. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(long addressId, Contacts contacts) throws ServiceException {
        if (addressId < 1 || contacts == null) {
            LOGGER.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            return contactsDAO.update(addressId, contacts);
        } catch (DAOException e) {
            LOGGER.error("[ContactsService] An exception occurs while updating Contacts. (id: {}). Exc: {}"
                    , addressId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long addressId) throws ServiceException {
        if (addressId < 1) {
            LOGGER.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            contactsDAO.delete(addressId);
        } catch (DAOException e) {
            LOGGER.error("[ContactsService] An exception occurs while deleting Contacts. (id: {}). Exc: {}"
                    , addressId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
