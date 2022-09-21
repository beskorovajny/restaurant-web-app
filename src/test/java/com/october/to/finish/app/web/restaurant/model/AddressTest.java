package com.october.to.finish.app.web.restaurant.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void constructorTest() {
        Address address =
                new Address("USA", "Seattle", "14Th", "12");
        assertEquals("USA", address.getCountry());
        assertEquals("Seattle", address.getCity());
        assertEquals("14Th", address.getStreet());
        assertEquals("12", address.getBuildingNumber());
    }

    @Test
    void getSetTest() {
        Address address = new Address();
        assertEquals(0, address.getId());
        assertNull(address.getCountry());
        assertNull(address.getCity());
        assertNull(address.getStreet());
        assertNull(address.getBuildingNumber());

        address.setId(1);
        address.setCountry("Canada");
        address.setCity("Toronto");
        address.setStreet("2th");
        address.setBuildingNumber("1");

        assertEquals(1, address.getId());
        assertEquals("Canada", address.getCountry());
        assertEquals("Toronto", address.getCity());
        assertEquals("2th", address.getStreet());
        assertEquals("1", address.getBuildingNumber());
    }

    @Test
    void wrongInputTest() {
        Address address = new Address();
        assertThrows(IllegalArgumentException.class,
                () -> new Address(null, "22", "11", "22"));
        assertThrows(IllegalArgumentException.class, () -> address.setBuildingNumber(null));
        assertThrows(IllegalArgumentException.class, () -> address.setStreet(null));
        assertThrows(IllegalArgumentException.class, () -> address.setCity(null));
        assertThrows(IllegalArgumentException.class, () -> address.setCountry(null));
    }

}