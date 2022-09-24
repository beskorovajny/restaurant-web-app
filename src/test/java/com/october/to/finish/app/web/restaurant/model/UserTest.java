package com.october.to.finish.app.web.restaurant.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    String password = "password";
    String email = "janed22oe@example.com";
    String phone = "555-000-272";
    CreditCard creditCard = new CreditCard();

    @Test
    void builderTest() {
        User user = User.newBuilder().setId(1).setEmail(email).setFirstName("Janke").setLastName("Donuts").
                setPhoneNumber(phone).setRoleId(User.Role.CLIENT.getId()).setCreditCard(creditCard).
                setPassword(password.toCharArray()).build();
        assertEquals(1, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals("Janke", user.getFirstName());
        assertEquals("Donuts", user.getLastName());
        assertEquals(phone, user.getPhoneNumber());
        assertEquals(User.Role.CLIENT.getId(), user.getRoleId());
        assertEquals(creditCard, user.getCreditCard());
        assertEquals(password, String.valueOf(user.getPassword()));
    }

    @Test
    void getSetTest() {
        User user = new User();
        assertEquals(0, user.getId());
        assertEquals(0, user.getRoleId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getEmail());
        assertNull(user.getPhoneNumber());
        assertNull(user.getCreditCard());
        assertNull(user.getPassword());

        user.setId(1);
        user.setCreditCard(creditCard);
        user.setRoleId(User.Role.MANAGER.getId());

        assertEquals(1, user.getId());
        assertEquals(creditCard, user.getCreditCard());
        assertEquals(User.Role.MANAGER.getId(), user.getRoleId());
    }

    @Test
    void wrongInputTest() {
        User user = User.newBuilder().build();
        assertThrows(IllegalArgumentException.class, () -> User.newBuilder().setId(0).setEmail(null).setFirstName(null).setLastName(null).setPhoneNumber(null).setRoleId(0).setCreditCard(null).setPassword(null).build());
        assertThrows(IllegalArgumentException.class, () -> user.setOrders(null));
        assertThrows(IllegalArgumentException.class, () -> user.setId(0));
        assertThrows(IllegalArgumentException.class, () -> user.setRoleId(0));
        assertThrows(IllegalArgumentException.class, () -> user.setCreditCard(null));
    }
}