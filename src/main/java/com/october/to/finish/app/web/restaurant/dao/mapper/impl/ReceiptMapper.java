package com.october.to.finish.app.web.restaurant.dao.mapper.impl;

import com.october.to.finish.app.web.restaurant.dao.mapper.ObjectMapper;
import com.october.to.finish.app.web.restaurant.model.Contacts;
import com.october.to.finish.app.web.restaurant.model.Receipt;
import com.october.to.finish.app.web.restaurant.model.User;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReceiptMapper implements ObjectMapper<Receipt> {
    @Override
    public Receipt extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, Receipt> receiptMap = new HashMap<>();
        Receipt receipt = Receipt.newBuilder().
                setId(resultSet.getLong("id")).
                setTimeCreated(resultSet.getTimestamp("created").toLocalDateTime()).
                setTotalPrice(resultSet.getBigDecimal("receipt_price").doubleValue()).
                setStatus(getById(resultSet.getLong("receipt_status_id"))).
                setContactsId(resultSet.getLong("contacts_id")).
                setCustomerId(resultSet.getLong("user_id")).
                build();
        receiptMap.put(String.valueOf(receipt.getId()), receipt);

        receipt = this.makeUnique(receiptMap, receipt);
        return receipt;
    }

    private Receipt.Status getById(Long id) {
        for (Receipt.Status s : Receipt.Status.values()) {
            if (s.getId() == (id)) return s;
        }
        return null;
    }

    @Override
    public Receipt makeUnique(Map<String, Receipt> cache, Receipt receipt) {
        cache.putIfAbsent(String.valueOf(receipt.getId()), receipt);
        return cache.get(String.valueOf(receipt.getId()));
    }

    public void setReceiptParams(Receipt receipt, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setTimestamp(1, Timestamp.valueOf(receipt.getDateCreated()));
        preparedStatement.setBigDecimal(2, BigDecimal.valueOf(receipt.getTotalPrice()));
        preparedStatement.setLong(3, receipt.getCustomerId());
        preparedStatement.setLong(4, receipt.getStatus().getId());
        preparedStatement.setLong(5, receipt.getContactsId());

    }

    public List<Receipt> extractReceipts(List<Receipt> receipts, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Optional<Receipt> receipt = Optional.
                    ofNullable(extractFromResultSet(resultSet));
            receipt.ifPresent(receipts::add);
        }
        return receipts;
    }
}
