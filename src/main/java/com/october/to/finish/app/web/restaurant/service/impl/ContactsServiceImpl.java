package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.dao.ContactsDAO;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Contacts;
import com.october.to.finish.app.web.restaurant.service.ContactsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * This class implements business logic for {@link Contacts}
 */
public class ContactsServiceImpl implements ContactsService {
    private static final Logger log = LogManager.getLogger(ContactsServiceImpl.class);
    private static final String NULL_ADDRESS_DAO_EXC = "[ContactsService] Can't create ContactsService with null input ContactsDAO";
    private static final String NULL_ADDRESS_INPUT_EXC = "[ContactsService] Can't operate null (or < 1) input!";
    private final ContactsDAO contactsDAO;

    public ContactsServiceImpl(ContactsDAO contactsDAO) {
        if (contactsDAO == null) {
            log.error(NULL_ADDRESS_DAO_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_DAO_EXC);
        }
        this.contactsDAO = contactsDAO;
    }

    @Override
    public void save(Contacts contacts) throws ServiceException {
        if (contacts == null) {
            log.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            contacts.setId(contactsDAO.save(contacts));
            log.info("[ContactsService] Contacts saved. (id: {})", contacts.getId());
        } catch (DAOException e) {
            log.error("[ContactsService] SQLException while saving Contacts; Exc: {}"
                    , e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Contacts findById(long addressId) throws ServiceException {
        if (addressId < 1) {
            log.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            return contactsDAO.findById(addressId);
        } catch (DAOException e) {
            log.error("[ContactsService] An exception occurs while receiving Contacts. (id: {}). Exc: {}"
                    , addressId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Contacts> findAll() throws ServiceException {
        try {
            return contactsDAO.findAll();
        } catch (DAOException e) {
            log.error("[ContactsService] An exception occurs while receiving Addresses. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(long addressId, Contacts contacts) throws ServiceException {
        if (addressId < 1 || contacts == null) {
            log.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            return contactsDAO.update(addressId, contacts);
        } catch (DAOException e) {
            log.error("[ContactsService] An exception occurs while updating Contacts. (id: {}). Exc: {}"
                    , addressId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(long addressId) throws ServiceException {
        if (addressId < 1) {
            log.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            contactsDAO.delete(addressId);
        } catch (DAOException e) {
            log.error("[ContactsService] An exception occurs while deleting Contacts. (id: {}). Exc: {}"
                    , addressId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
