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
        Dish dish = Dish.newBuilder().build();
        assertThrows(IllegalArgumentException.class, () -> Dish.newBuilder().
                setId(-1).
                setTitle(null).
                setDescription(null).
                setPrice(-1).
                setCategory(null).
                setWeightInGrams(0).
                setCount(-1).
                setMinutesToCook(-1).
                setDateCreated(null).
                build());
        assertThrows(IllegalArgumentException.class, () -> dish.setId(-1));
        assertThrows(IllegalArgumentException.class, () -> dish.setImage(null));
    }

}