package com.october.to.finish.app.web.restaurant.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.stream.DoubleStream;

public class Receipt {
    private long id;
    private User customer;
    private LocalDateTime dateCreated;
    private Status status;
    private int discount;
    private double totalPrice;
    private Address address;
    private Map<Dish, Integer> orderedDishes;

    public static Builder newBuilder() {
        return new Receipt().new Builder();
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

    public User getCustomer() {
        return customer;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public Status getStatus() {
        return status;
    }

    public int getDiscount() {
        return discount;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException();
        }
        this.address = address;
    }

    public Map<Dish, Integer> getOrderedDishes() {
        return orderedDishes;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setOrderedDishes(Map<Dish, Integer> orderedDishes) {
        if (orderedDishes == null) {
            throw new IllegalArgumentException("Dishes can't be null!");
        }
        this.orderedDishes = orderedDishes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return id == receipt.id && discount == receipt.discount && Double.compare(receipt.totalPrice, totalPrice) == 0 && Objects.equals(customer, receipt.customer) && Objects.equals(dateCreated, receipt.dateCreated) && status == receipt.status && Objects.equals(address, receipt.address) && Objects.equals(orderedDishes, receipt.orderedDishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, dateCreated, status, discount, totalPrice, address, orderedDishes);
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", customer=" + customer +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                ", address=" + address +
                '}';
    }

    public enum Status {
        NEW(1, "New"),
        COOKING(2, "Cooking"),
        DELIVERY(3, "Delivery"),
        COMPLETED(4, "Completed");
        private final long id;
        private final String status;

        Status(long id, String status) {
            this.id = id;
            this.status = status;
        }

        public long getId() {
            return id;
        }

        public String getStatus() {
            return status;
        }
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(long id) {
            if (id < 1) {
                throw new IllegalArgumentException("ID can't be < 1");
            }
            Receipt.this.id = id;
            return this;
        }

        public Builder setCustomer(User customer) {
            if (customer == null) {
                throw new IllegalArgumentException("Customer can't be null!");
            }
            Receipt.this.customer = customer;
            return this;
        }

        public Builder setTimeCreated(LocalDateTime timeCreated) {
            if (timeCreated == null) {
                throw new IllegalArgumentException("Creation time can't be null!");
            }
            Receipt.this.dateCreated = timeCreated;
            return this;
        }

        public Builder setStatus(Status status) {
            if (status == null) {
                throw new IllegalArgumentException("Status can't be null!");
            }
            Receipt.this.status = status;
            return this;
        }

        public Builder setDiscount(int discount) {
            if (discount < 0) {
                throw new IllegalArgumentException("Discount can't be null");
            }
            Receipt.this.discount = discount;
            return this;
        }

        public Receipt.Builder setAddress(Address address) {
            if (address == null) {
                throw new IllegalArgumentException("Address can't be null!");
            }
            Receipt.this.address = address;
            return this;
        }

        public Builder setOrderedDishes(Map<Dish, Integer> orderedDishes) {
            if (orderedDishes == null) {
                throw new IllegalArgumentException("Dishes can't be null!");
            }
            Receipt.this.orderedDishes = orderedDishes;
            return this;
        }

        public Receipt build() {
            return Receipt.this;
        }
    }
}
