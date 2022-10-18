package com.october.to.finish.app.web.restaurant.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactsTest {

    @Test
    void constructorTest() {
        Contacts contacts =
                new Contacts("USA", "Seattle", "14Th", "12", "380111111111");
        assertEquals("USA", contacts.getCountry());
        assertEquals("Seattle", contacts.getCity());
        assertEquals("14Th", contacts.getStreet());
        assertEquals("12", contacts.getBuildingNumber());
    }

    @Test
    void getSetTest() {
        Contacts contacts = new Contacts();
        assertEquals(0, contacts.getId());
        assertNull(contacts.getCountry());
        assertNull(contacts.getCity());
        assertNull(contacts.getStreet());
        assertNull(contacts.getBuildingNumber());
        assertNull(contacts.getPhone());

        contacts.setId(1);
        contacts.setCountry("Canada");
        contacts.setCity("Toronto");
        contacts.setStreet("2th");
        contacts.setBuildingNumber("1");
        contacts.setPhone("380000000000");

        assertEquals(1, contacts.getId());
        assertEquals("Canada", contacts.getCountry());
        assertEquals("Toronto", contacts.getCity());
        assertEquals("2th", contacts.getStreet());
        assertEquals("1", contacts.getBuildingNumber());
        assertEquals("380000000000", contacts.getPhone());
    }

    @Test
    void wrongInputTest() {
        Contacts contacts = new Contacts();
        assertThrows(IllegalArgumentException.class,
                () -> new Contacts(null, "22", "11", "22", null));
        assertThrows(IllegalArgumentException.class, () -> contacts.setBuildingNumber(null));
        assertThrows(IllegalArgumentException.class, () -> contacts.setStreet(null));
        assertThrows(IllegalArgumentException.class, () -> contacts.setCity(null));
        assertThrows(IllegalArgumentException.class, () -> contacts.setCountry(null));
        assertThrows(IllegalArgumentException.class, () -> contacts.setPhone(null));
    }

}