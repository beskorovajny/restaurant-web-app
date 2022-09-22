package com.october.to.finish.app.web.restaurant.sandbox;

import com.october.to.finish.app.web.restaurant.dao.CreditCardDAO;
import com.october.to.finish.app.web.restaurant.dao.UserDAO;
import com.october.to.finish.app.web.restaurant.dao.connections.ConnectionPoolHolder;
import com.october.to.finish.app.web.restaurant.dao.impl.CreditCardDAOImpl;
import com.october.to.finish.app.web.restaurant.dao.impl.UserDAOImpl;
import com.october.to.finish.app.web.restaurant.exceptions.DAOException;
import com.october.to.finish.app.web.restaurant.exceptions.FatalApplicationException;
import com.october.to.finish.app.web.restaurant.exceptions.ServiceException;
import com.october.to.finish.app.web.restaurant.model.CreditCard;
import com.october.to.finish.app.web.restaurant.model.User;
import com.october.to.finish.app.web.restaurant.security.PasswordEncryptionUtil;
import com.october.to.finish.app.web.restaurant.service.UserService;
import com.october.to.finish.app.web.restaurant.service.impl.UserServiceImpl;
import com.october.to.finish.app.web.restaurant.utils.db.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static final String PATH = "src/main/resources/mysql/db.properties";

    public static void main(String[] args) throws FatalApplicationException {
        System.out.println(PasswordEncryptionUtil.getEncrypted("sBBuE%0&V9h6"));
        System.out.println(PasswordEncryptionUtil.getEncrypted("U&nCjnNAHUvyQMtP"));

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
        /*Address address = new Address("Ukraine", "Lviv", "Science st.", "24");
        CreditCard creditCard = new CreditCard("PrivatBank", "5355-5555-5555-0001", 10000, "password".toCharArray());
        String password = "426HemiSixPack";
        User user = User.newBuilder()
                .setEmail("jan34424e@example.com")
                .setFirstName("Jae43")
                .setLastName("Dut34s")
                .setPhoneNumber("545-000-1122")
                .setRole(User.Role.UNAUTHORIZED_USER)
                .setCreditCard(creditCard)
                .setPassword(PasswordEncryptionUtil.getEncrypted(password).toCharArray()).build();
        LOGGER.info(String.valueOf(user.getPassword()));
        try {
            UserDAO userDAO = DAOFactory.getInstance().createUserDAO();
            UserService userService = new UserServiceImpl(userDAO);
            //userService.delete(8);
            userService.findAll().forEach(System.out::println);
            System.out.println(userService.findById(2));
            System.out.println(userService.findByEmail("johndoe4@example.com"));*/
            /*userDAO.updateUser(2, user);
            List<User> userList = userDAO.findAllUsers();
            userList.forEach(System.out::println);*/
            /*userDAO.delete(1);
            System.out.println(userDAO.findById(2));*/
        /* userDAO.deletePerson(2);*/
            /*Dish dish2 = Dish.newBuilder().setId(1)
                    .setTitle("Coffee")
                    .setDescription("description")
                    .setDateCreated(LocalDateTime.now())
                    .setMinutesToCook(0)
                    .setCategory(Dish.Category.DRINK)
                    .setWeightInGrams(350)
                    .setCount(40)
                    .setPrice(70).build();
            *//*dish.setId(dishDAO.save(dish));*//*
            dishDAO.save(dish2);*/



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

        /*} catch (SQLException | DAOException | ServiceException e) {
            throw new RuntimeException(e);
        }*/
        try {
            String password = "426HemiSixPack";
            CreditCard creditCard3 = new CreditCard("Bank Of Canada",
                    "2555555555555561", 5000.0, PasswordEncryptionUtil.getEncrypted("12343").toCharArray());
            CreditCard creditCard = new CreditCard("Bank Of Granada",
                    "4555555555555561", 5000.0, PasswordEncryptionUtil.getEncrypted("1234").toCharArray());
            CreditCard creditCard2 = new CreditCard("Bank Of Granada",
                    "5555-5555-5555-5555", 5000.0, PasswordEncryptionUtil.getEncrypted("1234").toCharArray());
            User user = User.newBuilder()
                    .setEmail("janov34222424e@example.com")
                    .setFirstName("2111")
                    .setLastName("Smith")
                    .setPhoneNumber("545-000-11211")
                    .setPassword(PasswordEncryptionUtil.getEncrypted(password).toCharArray()).
                    setRole(User.Role.UNAUTHORIZED_USER).build();
            user.setId(5);

            UserDAO userDAO = new UserDAOImpl(ConnectionPoolHolder.getDataSource(DBUtils.MYSQL_PROPS_PATH).getConnection());

            CreditCardDAO creditCardDAO = new CreditCardDAOImpl(ConnectionPoolHolder.getDataSource(DBUtils.MYSQL_PROPS_PATH).getConnection());
            UserService userService = new UserServiceImpl(userDAO, creditCardDAO);

            user.setCreditCard(userService.findCreditCard(user));
            /*userService.updateCreditCard(user, creditCard);*/
            /*userService.deleteCreditCard(user);*/
            /*userService.delete(6);*/
            userService.delete(3);
            System.out.println(user.getId());
        } catch (ServiceException | DAOException | SQLException e) {
            throw new FatalApplicationException(e.getMessage(), e);
        }
    }
}
