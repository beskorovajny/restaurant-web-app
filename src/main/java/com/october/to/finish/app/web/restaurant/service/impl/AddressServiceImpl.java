package com.october.to.finish.app.web.restaurant.service.impl;

import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.Address;
import com.october.to.finish.app.web.restaurant.service.AddressService;
import com.october.to.finish.app.web.restaurant.utils.db.DBUtils;
import com.october.to.finish.app.web.restaurant.dao.AddressDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class AddressServiceImpl implements AddressService {
    private static final Logger LOGGER = LogManager.getLogger(AddressServiceImpl.class);
    private static final String NULL_ADDRESS_DAO_EXC = "[AddressService] Can't create AddressService with null input AddressDAO";
    private static final String NULL_ADDRESS_INPUT_EXC = "[AddressService] Can't operate null input!";
    private static final String EXISTED_ADDRESS_EXC =
            "[AddressService] Address with given [ID: {}] is already existed!";
    private final AddressDAO addressDAO;

    public AddressServiceImpl(AddressDAO addressDAO) {
        if (addressDAO == null) {
            LOGGER.error(NULL_ADDRESS_DAO_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_DAO_EXC);
        }
        this.addressDAO = addressDAO;
    }

    @Override
    public boolean save(long userId, Address address) throws ServiceException {
        if (userId < 1 || address == null) {
            LOGGER.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            return checkAndSave(userId, address);
        } catch (SQLException e) {
            LOGGER.error("[AddressService] SQLException while saving Address (id: {}). Exc: {}"
                    , address.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private boolean checkAndSave(long userId, Address address) throws ServiceException, SQLException {
        addressDAO.getConnection().setAutoCommit(false);
        try {
            if (addressDAO.findById(address.getId()).getId() != 0) {
                DBUtils.rollback(addressDAO.getConnection());
                LOGGER.error(EXISTED_ADDRESS_EXC
                        , address.getId());
                throw new ServiceException(EXISTED_ADDRESS_EXC);
            } else {
                address.setId(addressDAO.save(userId, address));
            }
            addressDAO.getConnection().commit();
            addressDAO.getConnection().setAutoCommit(true);
            LOGGER.info("[AddressService] Address saved. (id: {})", address.getId());
            return true;
        } catch (DAOException e) {
            addressDAO.getConnection().rollback();
            LOGGER.error("[AddressService] Connection rolled back while saving Address. (id: {}). Exc: {}"
                    , address.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Address findById(long addressId) throws ServiceException {
        if (addressId < 1) {
            LOGGER.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            return addressDAO.findById(addressId);
        } catch (DAOException e) {
            LOGGER.error("[AddressService] An exception occurs while receiving Address. (id: {}). Exc: {}"
                    , addressId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Address> findAll() throws ServiceException {
        try {
            return addressDAO.findAll();
        } catch (DAOException e) {
            LOGGER.error("[AddressService] An exception occurs while receiving Addresses. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(long addressId, Address address) throws ServiceException {
        if (addressId < 1 || address == null) {
            LOGGER.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            return addressDAO.update(addressId, address);
        } catch (DAOException e) {
            LOGGER.error("[AddressService] An exception occurs while updating Address. (id: {}). Exc: {}"
                    , addressId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean updateByReceiptId(long receiptId, Address address) throws ServiceException {
        if (receiptId < 1 || address == null) {
            LOGGER.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            return addressDAO.updateByReceiptId(receiptId, address);
        } catch (DAOException e) {
            LOGGER.error("[AddressService] An exception occurs while updating Address(id:{}) for Receipt(id: {}. Exc: {}"
                    , address.getId(), receiptId, e.getMessage());
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
            addressDAO.delete(addressId);
        } catch (DAOException e) {
            LOGGER.error("[AddressService] An exception occurs while deleting Address. (id: {}). Exc: {}"
                    , addressId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteByReceiptId(long receiptId) throws ServiceException {
        if (receiptId < 1) {
            LOGGER.error(NULL_ADDRESS_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ADDRESS_INPUT_EXC);
        }
        try {
            addressDAO.deleteByReceiptId(receiptId);
        } catch (DAOException e) {
            LOGGER.error("[AddressService] An exception occurs while deleting Address for Receipt(id:{}). Exc: {}"
                    , receiptId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
