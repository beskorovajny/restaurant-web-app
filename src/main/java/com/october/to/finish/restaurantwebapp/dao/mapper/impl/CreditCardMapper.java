package com.october.to.finish.restaurantwebapp.dao.mapper.impl;

import com.october.to.finish.restaurantwebapp.dao.mapper.ObjectMapper;
import com.october.to.finish.restaurantwebapp.model.CreditCard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CreditCardMapper implements ObjectMapper<CreditCard> {

    @Override
    public CreditCard extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<Integer, CreditCard> cardMap = new HashMap<>();
        CreditCard card = new CreditCard();
        card.setCardNumber(resultSet.getString("card_number"));
        card.setBankName(resultSet.getString("bank_name"));
        card.setBalance(resultSet.getBigDecimal("balance").doubleValue());
        card.setPassword(resultSet.getString("password").toCharArray());

        cardMap.put(Integer.parseInt(card.getCardNumber()), card);

        card = this.makeUnique(cardMap, card);
        return card;
    }

    @Override
    public CreditCard makeUnique(Map<Integer, CreditCard> cache, CreditCard card) {
        cache.putIfAbsent(Integer.parseInt(card.getCardNumber()), card);
        return cache.get(Integer.parseInt(card.getCardNumber()));
    }
}
