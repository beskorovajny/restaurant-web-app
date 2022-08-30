package com.october.to.finish.restaurantwebapp.sandbox;

import com.october.to.finish.restaurantwebapp.dao.CreditCardDAO;
import com.october.to.finish.restaurantwebapp.dao.PersonDAO;
import com.october.to.finish.restaurantwebapp.dao.factory.DAOFactory;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.CreditCard;
import com.october.to.finish.restaurantwebapp.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
       /* Map<String, Dish> dishes = new HashMap<>();
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
        LOGGER.info(String.valueOf(person.getPassword()));*/
        try {
            PersonDAO personDAO = DAOFactory.getInstance().createPersonDAO();
            List<Person> personList = personDAO.findAllPersons();
            personList.forEach(System.out::println);
            personDAO.deletePerson(2);

           /* CreditCardDAO creditCardDAO = DAOFactory.getInstance().createCreditCardDAO();

            CreditCard creditCard = new CreditCard("Bank Of Africa",
                    "4555555555555550", 5000.0, "12349".toCharArray());

            CreditCard creditCard2 = new CreditCard("Bank Of England",
                    "4555555555555551", 5000.0, "12342".toCharArray());

            CreditCard creditCard3 = new CreditCard("Bank Of Canada",
                    "4555555555555561", 5000.0, "12343".toCharArray());
            CreditCard creditCard4 = new CreditCard("Bank Of Jamaica",
                    "4555555555555550", 5000.0, "12346".toCharArray());
            Person person = Person.newBuilder()
                    .setFirstName("John")
                    .setLastName("Doe")
                    .setEmail("john_doe@example.com")
                    .setPassword("12213312".toCharArray())
                    .setId(1).build();
           *//* creditCardDAO.insertCreditCard(creditCard, person.getId());*//*

            //CreditCard creditCard1 = creditCardDAO.getCreditCardByNumber(creditCard2.getCardNumber());

            creditCardDAO.insertCreditCard(creditCard2, 2);
            creditCardDAO.insertCreditCard(creditCard3, 3);
            creditCardDAO.insertCreditCard(creditCard4, 4);

            creditCardDAO.updateCreditCard(creditCard2.getCardNumber(), creditCard);
            creditCardDAO.deleteCreditCard(creditCard4.getCardNumber());*//*

            creditCardDAO.findAllCreditCards().forEach(System.out::println);*/

        } catch (SQLException | DAOException e) {
            throw new RuntimeException(e);
        }


    }
}
