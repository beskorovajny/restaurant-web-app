package com.october.to.finish.restaurantwebapp.model;

import java.util.Objects;

public class Address {


    private long id;
    private String country;
    private String city;
    private String street;
    private String buildingNumber;
    private String roomNumber;

    public Address() {
    }

    public Address(String country, String city, String street, String buildingNumber, String roomNumber) {
        if (country == null || city == null || street == null
                || buildingNumber == null || roomNumber == null) {
            throw new IllegalArgumentException("Cannot create address with given parameters...");
        }
        this.country = country;
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.roomNumber = roomNumber;
    }

    public void setId(long id) {
        if(id < 0) {
            throw new IllegalArgumentException("ID cannot be < 0");
        }
        this.id = id;
    }

    public long getId() {
        return id;
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

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        if (roomNumber == null) {
            throw new IllegalArgumentException("Room number can't be null!");
        }
        this.roomNumber = roomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id && Objects.equals(country, address.country) && Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(buildingNumber, address.buildingNumber) && Objects.equals(roomNumber, address.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, street, buildingNumber, roomNumber);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                '}';
    }
}
