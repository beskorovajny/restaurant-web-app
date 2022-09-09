package com.october.to.finish.restaurantwebapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {

    @Test
    void constructorTest() {
        CreditCard creditCard =
                new CreditCard("5_Bank", "5555-5555-5555-5555", 5555.5, "1111".toCharArray());
        assertEquals("5_Bank", creditCard.getBankName());
        assertEquals("5555-5555-5555-5555", creditCard.getCardNumber());
        assertEquals(5555.5, creditCard.getBalance());
        assertEquals("1111", String.valueOf(creditCard.getPassword()));
    }

    @Test
    void getSetTest() {
        CreditCard creditCard = new CreditCard();
        assertNull(creditCard.getBankName());
        assertNull(creditCard.getCardNumber());
        assertEquals(0, creditCard.getBalance());
        assertNull(creditCard.getPassword());

        creditCard.setBankName("CreditBank");
        creditCard.setCardNumber("5555-5555-5555-5551");
        creditCard.setBalance(10);
        creditCard.setPassword("sec".toCharArray());

        assertEquals("CreditBank", creditCard.getBankName());
        assertEquals("5555-5555-5555-5551", creditCard.getCardNumber());
        assertEquals(10, creditCard.getBalance());
        assertEquals("sec", String.valueOf(creditCard.getPassword()));
    }

    @Test
    void wrongInputTest() {
        CreditCard creditCard = new CreditCard();
        assertThrows(IllegalArgumentException.class,
                () -> new CreditCard(null, "22", -23, null));
        assertThrows(IllegalArgumentException.class, () -> creditCard.setBankName(null));
        assertThrows(IllegalArgumentException.class, () -> creditCard.setCardNumber(null));
        assertThrows(IllegalArgumentException.class, () -> creditCard.setBalance(-20));
        assertThrows(IllegalArgumentException.class, () -> creditCard.setPassword(null));
    }

}