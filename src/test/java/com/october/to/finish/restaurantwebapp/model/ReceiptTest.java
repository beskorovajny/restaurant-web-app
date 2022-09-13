package com.october.to.finish.restaurantwebapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {

    @Test
    void builderTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-09-09 16:25:53", formatter);
        Map<String, Dish> dishMap = Map.of("1", Dish.newBuilder().setPrice(10).setCount(1).build()
                , "2", Dish.newBuilder().setPrice(40).setCount(1).build()
                , "3", Dish.newBuilder().setPrice(50).setCount(1).build());
        User user = User.newBuilder().setFirstName("John").build();
        Receipt receipt = Receipt.newBuilder().
                setId(1).
                setCustomer(user).
                setTimeCreated(dateTime).
                setStatus(Receipt.Status.NEW).
                setDiscount(10).
                setOrderedDishes(dishMap).
                build();
        assertEquals(1, receipt.getId());
        assertEquals(user, receipt.getCustomer());
        assertEquals(dateTime, receipt.getDateCreated());
        assertEquals(Receipt.Status.NEW, receipt.getStatus());
        assertEquals(10, receipt.getDiscount());
        assertEquals(dishMap, receipt.getOrderedDishes());
        assertEquals(90, receipt.getTotalPrice());

        receipt.setTotalPrice(150);
        assertEquals(150, receipt.getTotalPrice());
    }

    @Test
    void getSetTest() {
        Map<String, Dish> dishMap = Map.of("1", Dish.newBuilder().setPrice(10).setCount(1).build()
                , "2", Dish.newBuilder().setPrice(40).setCount(1).build()
                , "3", Dish.newBuilder().setPrice(50).setCount(1).build());
        Receipt receipt = new Receipt();
        assertNull(receipt.getOrderedDishes());

        receipt.setTotalPrice(20);
        assertEquals(20, receipt.getTotalPrice());

        receipt.setTotalPrice(0);
        receipt.setOrderedDishes(dishMap);
        assertEquals(dishMap, receipt.getOrderedDishes());
        assertEquals(100, receipt.getTotalPrice());
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
                        build());
        assertThrows(IllegalArgumentException.class, () -> receipt.setTotalPrice(-29));
        assertThrows(IllegalArgumentException.class, () -> receipt.setOrderedDishes(null));
    }
}