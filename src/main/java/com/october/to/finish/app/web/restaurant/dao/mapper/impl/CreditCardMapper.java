package com.october.to.finish.app.web.restaurant.dao.mapper.impl;

import com.october.to.finish.app.web.restaurant.model.CreditCard;
import com.october.to.finish.app.web.restaurant.dao.mapper.ObjectMapper;
import com.october.to.finish.app.web.restaurant.security.PasswordEncryptionUtil;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CreditCardMapper implements ObjectMapper<CreditCard> {

    @Override
    public CreditCard extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, CreditCard> cardMap = new HashMap<>();
        CreditCard card = new CreditCard();
        card.setCardNumber(resultSet.getString("card_number"));
        card.setBankName(resultSet.getString("bank_name"));
        card.setBalance(resultSet.getBigDecimal("balance").doubleValue());
        card.setPassword(resultSet.getString("password").toCharArray());

        cardMap.put(card.getCardNumber(), card);

        card = this.makeUnique(cardMap, card);
        return card;
    }

    @Override
    public CreditCard makeUnique(Map<String, CreditCard> cache, CreditCard card) {
        cache.putIfAbsent(card.getCardNumber(), card);
        return cache.get(card.getCardNumber());
    }

    public void setCreditCardParams(CreditCard creditCard, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, creditCard.getCardNumber());
        preparedStatement.setString(2, creditCard.getBankName());
        preparedStatement.setBigDecimal(3, BigDecimal.valueOf(creditCard.getBalance()));
        preparedStatement.setString(4, PasswordEncryptionUtil.
                getEncrypted(String.valueOf(creditCard.getPassword())));
    }

    public List<CreditCard> extractCreditCards(List<CreditCard> creditCards, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Optional<CreditCard> creditCard = Optional.
                    ofNullable(extractFromResultSet(resultSet));
            creditCard.ifPresent(creditCards::add);
        }
        return creditCards;
    }
}
