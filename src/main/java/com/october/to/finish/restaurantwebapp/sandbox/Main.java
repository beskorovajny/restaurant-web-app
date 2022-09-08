package com.october.to.finish.restaurantwebapp.sandbox;

import com.october.to.finish.restaurantwebapp.dao.UserDAO;
import com.october.to.finish.restaurantwebapp.dao.factory.DAOFactory;
import com.october.to.finish.restaurantwebapp.exceptions.DAOException;
import com.october.to.finish.restaurantwebapp.model.Address;
import com.october.to.finish.restaurantwebapp.model.CreditCard;
import com.october.to.finish.restaurantwebapp.model.User;
import com.october.to.finish.restaurantwebapp.security.PasswordEncryptionUtil;
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
        LOGGER.error("Fatal error...");*/
        Address address = new Address("Ukraine", "Lviv", "Science st.", "24", "16");
        CreditCard creditCard = new CreditCard("PrivatBank", "5555-5555-5555-0001", 10000, "password".toCharArray());
        String password = "426HemiSixPack";
        User user = User.newBuilder()
                .setEmail("janed22oe@example.com")
                .setFirstName("Janke")
                .setLastName("Donuts")
                .setPhoneNumber("555-000-272")
                .setRole(User.Role.UNAUTHORIZED_USER)
                .setAddress(address)
                .setCreditCard(creditCard)
                .setPassword(PasswordEncryptionUtil.getEncrypted(password).toCharArray()).build();
        LOGGER.info(String.valueOf(user.getPassword()));
        try {
            UserDAO userDAO = DAOFactory.getInstance().createUserDAO();
            /*userDAO.updateUser(2, user);
            List<User> userList = userDAO.findAllUsers();
            userList.forEach(System.out::println);*/
            userDAO.deleteUser(1);
            System.out.println(userDAO.getUserById(7));
            /* userDAO.deletePerson(2);*/

           /* CreditCardDAO creditCardDAO = DAOFactory.getInstance().createCreditCardDAO();

            CreditCard creditCard = new CreditCard("Bank Of Africa",
                    "4555555555555550", 5000.0, "12349".toCharArray());

            CreditCard creditCard2 = new CreditCard("Bank Of England",
                    "4555555555555551", 5000.0, "12342".toCharArray());

            CreditCard creditCard3 = new CreditCard("Bank Of Canada",
                    "4555555555555561", 5000.0, "12343".toCharArray());
            CreditCard creditCard4 = new CreditCard("Bank Of Jamaica",
                    "4555555555555550", 5000.0, "12346".toCharArray());
            Person user = Person.newBuilder()
                    .setFirstName("John")
                    .setLastName("Doe")
                    .setEmail("john_doe@example.com")
                    .setPassword("12213312".toCharArray())
                    .setId(1).build();
           *//* creditCardDAO.insertCreditCard(creditCard, user.getId());*//*

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
