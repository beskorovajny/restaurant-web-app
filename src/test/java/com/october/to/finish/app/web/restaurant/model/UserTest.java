package com.october.to.finish.app.web.restaurant.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    String password = "password";
    String email = "janed22oe@example.com";
    String phone = "555-000-272";

    @Test
    void builderTest() {
        User user = User.newBuilder().setId(1).setEmail(email).setFirstName("Janke").setLastName("Donuts").
                setPhoneNumber(phone).setRole(User.Role.CLIENT).
                setPassword(password.toCharArray()).build();
        assertEquals(1, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals("Janke", user.getFirstName());
        assertEquals("Donuts", user.getLastName());
        assertEquals(phone, user.getPhoneNumber());
        assertEquals(User.Role.CLIENT.getId(), user.getRole().getId());
        assertEquals(password, String.valueOf(user.getPassword()));
    }

    @Test
    void getSetTest() {
        User user = new User();
        assertEquals(0, user.getId());
        assertNull(user.getRole());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getEmail());
        assertNull(user.getPhoneNumber());
        assertNull(user.getPassword());

        user.setId(1);
        user.setRole(User.Role.MANAGER);

        assertEquals(1, user.getId());
        assertEquals(User.Role.MANAGER.getId(), user.getRole().getId());
    }

    @Test
    void wrongInputTest() {
        User user = User.newBuilder().build();
        assertThrows(IllegalArgumentException.class, () -> User.newBuilder().setId(0).setEmail(null).setFirstName(null).setLastName(null)
                .setPhoneNumber(null).setRole(null).setPassword(null).build());
        assertThrows(IllegalArgumentException.class, () -> user.setOrders(null));
        assertThrows(IllegalArgumentException.class, () -> user.setId(0));
        assertThrows(IllegalArgumentException.class, () -> user.setRole(null));
    }
}