package com.october.to.finish.app.web.restaurant.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class Receipt {
    private long id;
    private long customerId;
    private LocalDateTime dateCreated;
    private Status status;
    private double totalPrice;
    private long contactsId;
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

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("CustomerID can't be less than zero");
        }
        this.customerId = customerId;
    }

    public long getContactsId() {
        return contactsId;
    }

    public void setContactsId(long contactsId) {
        if (contactsId <= 0) {
            throw new IllegalArgumentException("ContactsID can't be less than zero");
        }
        this.contactsId = contactsId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Map<Dish, Integer> getOrderedDishes() {
        return orderedDishes;
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
        return id == receipt.id && customerId == receipt.customerId && Double.compare(receipt.totalPrice, totalPrice) == 0 && contactsId == receipt.contactsId && Objects.equals(dateCreated, receipt.dateCreated) && status == receipt.status && Objects.equals(orderedDishes, receipt.orderedDishes);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Total price can't be less than zero");
        }
        this.totalPrice = totalPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, dateCreated, status, totalPrice, contactsId, orderedDishes);
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", contactsId=" + contactsId +
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

        public Builder setCustomerId(long customerId) {
            if (customerId < 0) {
                throw new IllegalArgumentException("CustomerId can't be <= 0!");
            }
            Receipt.this.customerId = customerId;
            return this;
        }

        public Builder setTimeCreated(LocalDateTime timeCreated) {
            if (timeCreated == null) {
                throw new IllegalArgumentException("Creation time can't be null!");
            }
            Receipt.this.dateCreated = timeCreated;
            return this;
        }

        public Builder setTotalPrice(double totalPrice) {
            if (totalPrice < 0) {
                throw new IllegalArgumentException("Total price can't be < 0!");
            }
            Receipt.this.totalPrice = totalPrice;
            return this;
        }

        public Builder setStatus(Status status) {
            if (status == null) {
                throw new IllegalArgumentException("Status can't be null!");
            }
            Receipt.this.status = status;
            return this;
        }

        public Receipt.Builder setContactsId(long contactsId) {
            if (contactsId <= 0) {
                throw new IllegalArgumentException("ContactsId can't be <= 0!");
            }
            Receipt.this.contactsId = contactsId;
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
