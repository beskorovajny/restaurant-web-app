package com.october.to.finish.app.web.restaurant.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DishTest {

    @Test
    void builderTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-09-09 16:25:53", formatter);
        Dish dish = Dish.newBuilder()
                .setId(1)
                .setTitle("Coffee")
                .setDescription("description")
                .setDateCreated(dateTime)
                .setCooking(5)
                .setCategory(Dish.Category.DRINK)
                .setWeight(350)
                .setPrice(70).build();
        assertNotNull(dish);
        assertEquals(1, dish.getId());
        assertEquals("Coffee", dish.getTitle());
        assertEquals("description", dish.getDescription());
        assertEquals("2022-09-09 16:25:53", dish.getDateCreated().format(formatter));
        assertEquals(5, dish.getCooking());
        assertEquals(Dish.Category.DRINK.getId(), dish.getCategory().getId());
        assertEquals(350, dish.getWeight());
        assertEquals(70, dish.getPrice());
        assertEquals(0, dish.getTotalPrice());

        dish.setId(2);
        dish.setTotalPrice(300.0);
        assertEquals(2, dish.getId());
        assertEquals(300.0, dish.getTotalPrice());
    }

    @Test
    void wrongInputTest() {
        Dish dish = Dish.newBuilder().build();
        assertThrows(IllegalArgumentException.class, () -> Dish.newBuilder().
                setId(-1).
                setTitle(null).
                setDescription(null).
                setPrice(-1).
                setCategory(null).
                setWeight(0).
                setCooking(-1).
                setDateCreated(null).
                build());
        assertThrows(IllegalArgumentException.class, () -> dish.setId(-1));
        assertThrows(IllegalArgumentException.class, () -> dish.setTotalPrice(-1));
    }

}