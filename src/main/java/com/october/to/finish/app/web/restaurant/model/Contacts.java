package com.october.to.finish.app.web.restaurant.model;

import java.util.Objects;

public class Contacts {
    private long id;
    private String country;
    private String city;
    private String street;
    private String buildingNumber;
    private String phone;

    public Contacts() {
    }

    public Contacts(String country, String city, String street, String buildingNumber, String phone) {
        if (country == null || city == null || street == null || buildingNumber == null || phone == null) {
            throw new IllegalArgumentException("Cannot create address with given parameters...");
        }
        this.country = country;
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("ID can't be < 1");
        }
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country == null) {
            throw new IllegalArgumentException("Country can't be null!");
        }
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city == null) {
            throw new IllegalArgumentException("City can't be null");
        }
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street == null) {
            throw new IllegalArgumentException("Street can't be null!");
        }
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        if (buildingNumber == null) {
            throw new IllegalArgumentException("Building number can't be null!");
        }
        this.buildingNumber = buildingNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone number can't be null or empty");
        }
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contacts contacts = (Contacts) o;
        return id == contacts.id && Objects.equals(country, contacts.country)
                && Objects.equals(city, contacts.city) && Objects.equals(street, contacts.street)
                && Objects.equals(buildingNumber, contacts.buildingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, street, buildingNumber);
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                '}';
    }
}
