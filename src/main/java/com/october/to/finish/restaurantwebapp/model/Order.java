package com.october.to.finish.restaurantwebapp.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.DoubleStream;

public class Order {
    private long id;
    private Person customer;
    private LocalDateTime dateCreated;
    private Status status;
    private int discount;

    private Set<Dish> orderedDishes;

    public static Builder newBuilder() {
        return new Order().new Builder();
    }

    public long getId() {
        return id;
    }

    public Person getCustomer() {
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

    public Set<Dish> getOrderedDishes() {
        return orderedDishes;
    }

    public void setOrderedDishes(Set<Dish> orderedDishes) {
        this.orderedDishes = orderedDishes;
    }

    public double getTotalPrice() {
        if (discount > 0) {
            return orderedDishes.stream()
                    .flatMapToDouble(e -> DoubleStream
                            .of((e.getPrice() - ((e.getPrice() / 100) * discount)) * e.getCount())).sum();
        }
        return orderedDishes.stream().flatMapToDouble(e -> DoubleStream.of(e.getPrice() * e.getCount())).sum();
    }

    public enum Status {
        NEW("new"),
        COOKING("cooking"),
        DELIVERY("delivery"),
        COMPLETED("completed");

        private final String statusTitle;

        Status(String statusTitle) {
            this.statusTitle = statusTitle;
        }

        public String getStatusTitle() {
            return statusTitle;
        }
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(long id) {
            if (id < 0) {
                throw new IllegalArgumentException("ID can't be < 0!");
            }
            Order.this.id = id;
            return this;
        }

        public Builder setCustomer(Person customer) {
            if (customer == null) {
                throw new IllegalArgumentException("Customer can't be null!");
            }
            Order.this.customer = customer;
            return this;
        }

        public Builder setTimeCreated() {
            Order.this.dateCreated = LocalDateTime.now();
            return this;
        }

        public Builder setStatus(Status status) {
            if (status == null) {
                throw new IllegalArgumentException("Status can't be null!");
            }
            Order.this.status = status;
            return this;
        }

        public Builder setDiscount(int discount) {
            if (discount < 0) {
                throw new IllegalArgumentException("Discount can't be null");
            }
            Order.this.discount = discount;
            return this;
        }

        public Builder setOrderedDishes(Set<Dish> orderedDishes) {
            if (orderedDishes == null) {
                throw new IllegalArgumentException("Dishes can't be null!");
            }
            Order.this.orderedDishes = orderedDishes;
            return this;
        }

        public Order build() {
            return Order.this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && discount == order.discount && Objects.equals(customer, order.customer) && Objects.equals(dateCreated, order.dateCreated) && status == order.status && Objects.equals(orderedDishes, order.orderedDishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, dateCreated, status, discount, orderedDishes);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                ", discount=" + discount +
                ", orderedDishes=" + orderedDishes +
                '}';
    }
}
