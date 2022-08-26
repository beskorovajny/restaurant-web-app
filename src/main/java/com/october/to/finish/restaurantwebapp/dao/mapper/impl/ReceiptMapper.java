package com.october.to.finish.restaurantwebapp.dao.mapper.impl;

import com.october.to.finish.restaurantwebapp.dao.mapper.ObjectMapper;
import com.october.to.finish.restaurantwebapp.model.Receipt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ReceiptMapper implements ObjectMapper<Receipt> {
    @Override
    public Receipt extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, Receipt> receiptMap = new HashMap<>();
        Receipt receipt = Receipt.newBuilder().setId(resultSet.getLong("id")).
                setTimeCreated(resultSet.getTimestamp("time_created").toLocalDateTime()).
                setDiscount(resultSet.getInt("discount")).
                setTotalPrice(resultSet.getBigDecimal("total_price").doubleValue()).
                build();


        receiptMap.put(String.valueOf(receipt.getId()), receipt);

        receipt = this.makeUnique(receiptMap, receipt);
        return receipt;
    }

    @Override
    public Receipt makeUnique(Map<String, Receipt> cache, Receipt receipt) {
        cache.putIfAbsent(String.valueOf(receipt.getId()), receipt);
        return cache.get(String.valueOf(receipt.getId()));
    }
}
