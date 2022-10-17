package com.october.to.finish.app.web.restaurant.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {
    Address address = new Address();

    @Test
    void builderTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-09-09 16:25:53", formatter);
        Map<Dish, Integer> dishMap = Map.of(Dish.newBuilder().setPrice(10).build(), 1,
                Dish.newBuilder().setPrice(40).build(), 2,
                Dish.newBuilder().setPrice(50).build(), 3);
        User user = User.newBuilder().setFirstName("John").build();
        Receipt receipt = Receipt.newBuilder().
                setId(1).
                setCustomer(user).
                setTimeCreated(dateTime).
                setStatus(Receipt.Status.NEW).
                setDiscount(10).
                setOrderedDishes(dishMap).
                setAddress(address).
                build();
        assertEquals(1, receipt.getId());
        assertEquals(user, receipt.getCustomer());
        assertEquals(dateTime, receipt.getDateCreated());
        assertEquals(Receipt.Status.NEW, receipt.getStatus());
        assertEquals(10, receipt.getDiscount());
        assertEquals(dishMap, receipt.getOrderedDishes());
        assertEquals(address, receipt.getAddress());
    }

    @Test
    void getSetTest() {
        Map<Dish, Integer> dishMap = Map.of(Dish.newBuilder().setPrice(10).build(), 1,
                Dish.newBuilder().setPrice(40).build(), 2,
                Dish.newBuilder().setPrice(50).build(), 3);
        Receipt receipt = new Receipt();
        assertNull(receipt.getOrderedDishes());
        assertNull(receipt.getAddress());

        receipt.setOrderedDishes(dishMap);
        receipt.setAddress(address);
        assertEquals(dishMap, receipt.getOrderedDishes());
        assertEquals(address, receipt.getAddress());
    }

    @Test
    void wrongInputTest() {
        Receipt receipt = Receipt.newBuilder().build();
        assertThrows(IllegalArgumentException.class,
                () -> Receipt.newBuilder().
                        setId(-1).
                        setCustomer(null).
                        setTimeCreated(null).
                        setStatus(null).
                        setDiscount(-2).
                        setOrderedDishes(null).
                        setAddress(null).
                        build());
        assertThrows(IllegalArgumentException.class, () -> receipt.setOrderedDishes(null));
        assertThrows(IllegalArgumentException.class, () -> receipt.setAddress(null));

    }
}