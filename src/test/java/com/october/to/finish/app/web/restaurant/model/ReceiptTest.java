package com.october.to.finish.app.web.restaurant.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {
    private Contacts contacts;
    private User user;

    @BeforeEach
    void init() {
        contacts = new Contacts();
        contacts.setId(2);
        user = User.newBuilder().setId(1).setFirstName("John").build();
        user.setId(1);
    }

    @Test
    void builderTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-09-09 16:25:53", formatter);
        Map<Dish, Integer> dishMap = Map.of(Dish.newBuilder().setPrice(10).build(), 1,
                Dish.newBuilder().setPrice(40).build(), 2,
                Dish.newBuilder().setPrice(50).build(), 3);

        Receipt receipt = Receipt.newBuilder().
                setId(1).
                setCustomerId(user.getId()).
                setTimeCreated(dateTime).
                setStatus(Receipt.Status.NEW).
                setOrderedDishes(dishMap).
                setContactsId(contacts.getId()).
                build();
        assertEquals(1, receipt.getId());
        assertEquals(user.getId(), receipt.getCustomerId());
        assertEquals(dateTime, receipt.getDateCreated());
        assertEquals(Receipt.Status.NEW, receipt.getStatus());
        assertEquals(dishMap, receipt.getOrderedDishes());
        assertEquals(contacts.getId(), receipt.getContactsId());
    }

    @Test
    void getSetTest() {
        Map<Dish, Integer> dishMap = Map.of(Dish.newBuilder().setPrice(10).build(), 1,
                Dish.newBuilder().setPrice(40).build(), 2,
                Dish.newBuilder().setPrice(50).build(), 3);
        Receipt receipt = new Receipt();
        assertNull(receipt.getOrderedDishes());
        assertEquals(0, receipt.getCustomerId());
        assertEquals(0, receipt.getContactsId());

        receipt.setOrderedDishes(dishMap);
        receipt.setContactsId(contacts.getId());

        assertEquals(dishMap, receipt.getOrderedDishes());
        assertEquals(contacts.getId(), receipt.getContactsId());
    }

    @Test
    void wrongInputTest() {
        Receipt receipt = Receipt.newBuilder().build();
        assertThrows(IllegalArgumentException.class,
                () -> Receipt.newBuilder().
                        setId(-1).
                        setCustomerId(0).
                        setTimeCreated(null).
                        setStatus(null).
                        setOrderedDishes(null).
                        setContactsId(0).
                        build());
        assertThrows(IllegalArgumentException.class, () -> receipt.setOrderedDishes(null));
        assertThrows(IllegalArgumentException.class, () -> receipt.setCustomerId(-1));
        assertThrows(IllegalArgumentException.class, () -> receipt.setContactsId(-1));
    }
}