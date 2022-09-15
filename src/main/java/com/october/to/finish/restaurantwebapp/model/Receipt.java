package com.october.to.finish.restaurantwebapp.model;

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
    private Map<String, Dish> orderedDishes;

    public static Builder newBuilder() {
        return new Receipt().new Builder();
    }

    public long getId() {
        return id;
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

    public Map<String, Dish> getOrderedDishes() {
        return orderedDishes;
    }

    public void setOrderedDishes(Map<String, Dish> orderedDishes) {
        if (orderedDishes == null) {
            throw new IllegalArgumentException("Dishes can't be null!");
        }
        this.orderedDishes = orderedDishes;
    }

    public double getTotalPrice() {
        if (totalPrice == 0) {
            totalPrice = calculateTotalPrice();
        }
        return totalPrice;
    }

    private double calculateTotalPrice() {
        if (discount > 0) {
            return orderedDishes.entrySet().stream()
                    .flatMapToDouble(e -> DoubleStream
                            .of((e.getValue().getPrice() -
                                    ((e.getValue().getPrice() / 100) * discount)) * e.getValue().getCount())).sum();
        }
        return orderedDishes.entrySet().stream()
                .flatMapToDouble(e -> DoubleStream.of(e.getValue().getPrice() * e.getValue().getCount())).sum();
    }

    public void setTotalPrice(double totalPrice) {
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Price can't be < 0");
        }
        this.totalPrice = totalPrice;
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
                ", dateCreated=" + dateCreated.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                ", status=" + status +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                ", address=" + address +
                ", orderedDishes=" + orderedDishes +
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
            if (id < 0) {
                throw new IllegalArgumentException("ID can't be < 0!");
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

        public Builder setOrderedDishes(Map<String, Dish> orderedDishes) {
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
