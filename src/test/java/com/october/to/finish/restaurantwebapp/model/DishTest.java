package com.october.to.finish.restaurantwebapp.model;

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
                .setMinutesToCook(5)
                .setCategory(Dish.Category.DRINK)
                .setWeightInGrams(350)
                .setCount(40)
                .setImage(new byte[]{1})
                .setPrice(70).build();
        assertNotNull(dish);
        assertEquals(1, dish.getId());
        assertEquals("Coffee", dish.getTitle());
        assertEquals("description", dish.getDescription());
        assertEquals("2022-09-09 16:25:53", dish.getDateCreated().format(formatter));
        assertEquals(5, dish.getMinutesToCook());
        assertEquals(Dish.Category.DRINK.getId(), dish.getCategory().getId());
        assertEquals(350, dish.getWeightInGrams());
        assertEquals(40, dish.getCount());
        assertEquals(70, dish.getPrice());
        assertEquals(Arrays.toString(new byte[]{1}), Arrays.toString(dish.getImage()));

        dish.setId(2);
        dish.setImage(new byte[]{3, 3, 3});
        assertEquals(2, dish.getId());
        assertEquals(Arrays.toString(new byte[]{3, 3, 3}), Arrays.toString(dish.getImage()));
    }

    @Test
    void wrongInputTest() {
        assertThrows(IllegalArgumentException.class, () -> Dish.newBuilder().setId(-1).build());
        assertThrows(IllegalArgumentException.class, () -> Dish.newBuilder().setTitle(null));
        assertThrows(IllegalArgumentException.class, () -> Dish.newBuilder().setDescription(null));
        assertThrows(IllegalArgumentException.class, () -> Dish.newBuilder().setPrice(-1));
        assertThrows(IllegalArgumentException.class, () -> Dish.newBuilder().setCategory(null));
        assertThrows(IllegalArgumentException.class, () -> Dish.newBuilder().setWeightInGrams(0));
        assertThrows(IllegalArgumentException.class, () -> Dish.newBuilder().setCount(-1));
        assertThrows(IllegalArgumentException.class, () -> Dish.newBuilder().setMinutesToCook(-1));
        assertThrows(IllegalArgumentException.class, () -> Dish.newBuilder().setDateCreated(null));
    }

}