package com.october.to.finish.restaurantwebapp.sandbox;

import com.october.to.finish.restaurantwebapp.model.Dish;
import com.october.to.finish.restaurantwebapp.model.Person;
import com.october.to.finish.restaurantwebapp.model.Receipt;
import com.october.to.finish.restaurantwebapp.security.PasswordEncryptionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Map<String, Dish> dishes = new HashMap<>();
        Dish dish1 = Dish.newBuilder().setId(1)
                .setTitle("Pizza")
                .setTitleCyrillic("Піца")
                .setDescription("description")
                .setDescriptionCyrillic("Опис...")
                .setDateCreated()
                .setMinutesToCook(25)
                .setCategory(Dish.Category.PIZZA)
                .setWeightInGrams(425)
                .setCount(5)
                .setPrice(200).build();
        LOGGER.info("Dish : {} was created", dish1);
        Dish dish2 = Dish.newBuilder().setId(1)
                .setTitle("Drink")
                .setTitleCyrillic("Напій")
                .setDescription("description")
                .setDescriptionCyrillic("Опис...")
                .setDateCreated()
                .setMinutesToCook(0)
                .setCategory(Dish.Category.DRINK)
                .setWeightInGrams(700)
                .setCount(5)
                .setPrice(200).build();
        LOGGER.info("Dish : {} was created", dish2);
        dishes.put(dish1.getTitle(), dish1);
        dishes.put(dish2.getTitle(), dish2);
        Receipt receipt = Receipt.newBuilder()
                .setId(1)
                .setCustomer(new Person())
                .setStatus(Receipt.Status.NEW)
                .setTimeCreated()
                .setDiscount(75)
                .setOrderedDishes(dishes).build();
        LOGGER.info("Receipt : {} was created", receipt);
        System.out.println(receipt.getTotalPrice());


        receipt.getOrderedDishes().entrySet().forEach(System.out::println);
        System.out.println(receipt);
        LOGGER.error("Fatal error...");
        String password = "440HemiSixPack";
        Person person = Person.newBuilder()
                .setPassword(PasswordEncryptionUtil.getEncrypted(password).toCharArray()).build();
        LOGGER.info(String.valueOf(person.getPassword()));


    }
}
