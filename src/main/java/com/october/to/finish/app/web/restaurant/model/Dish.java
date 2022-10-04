package com.october.to.finish.app.web.restaurant.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class Dish {
    private long id;
    private String title;
    private String description;
    private Category category;
    private double price;
    private int weight;
    private int cooking;
    private LocalDateTime dateCreated;
    private byte[] image;

    public static Builder newBuilder() {
        return new Dish().new Builder();
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }

    public int getCooking() {
        return cooking;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id == dish.id && Double.compare(dish.price, price) == 0 && weight == dish.weight && cooking == dish.cooking && Objects.equals(title, dish.title) && Objects.equals(description, dish.description) && category == dish.category && Objects.equals(dateCreated, dish.dateCreated) && Arrays.equals(image, dish.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, description, category, price, weight, cooking, dateCreated);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", weight=" + weight +
                ", cooking=" + cooking +
                ", dateCreated=" + dateCreated +
                '}';
    }

    public enum Category {
        SALAD(1, "Salad"),
        PIZZA(2, "Pizza"),
        APPETIZER(3, "Appetizer"),
        DRINK(4, "Drink");

        private final long id;
        private final String categoryName;

        Category(long id, String categoryName) {
            this.id = id;
            this.categoryName = categoryName;
        }

        public long getId() {
            return id;
        }

        public String getCategoryName() {
            return categoryName;
        }
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(long id) {
            if (id < 1) {
                throw new IllegalArgumentException("ID can't be < 1");
            }
            Dish.this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            if (title == null) {
                throw new IllegalArgumentException("Title can't be null!");
            }
            Dish.this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            if (description == null) {
                throw new IllegalArgumentException("Description can't be null");
            }
            Dish.this.description = description;
            return this;
        }

        public Builder setCategory(Category category) {
            if (category == null) {
                throw new IllegalArgumentException("Category can't be null!");
            }
            Dish.this.category = category;
            return this;
        }

        public Builder setPrice(double price) {
            if (price < 0) {
                throw new IllegalArgumentException("Price can't be < 0");
            }
            Dish.this.price = price;
            return this;
        }

        public Builder setWeight(int weightInGrams) {
            if (weightInGrams < 1) {
                throw new IllegalArgumentException("Weight can't be < 0");
            }
            Dish.this.weight = weightInGrams;
            return this;
        }

        public Builder setCooking(int minutes) {
            if (minutes < 0) {
                throw new IllegalArgumentException("Time can't be < 0");
            }
            Dish.this.cooking = minutes;
            return this;
        }

        public Builder setDateCreated(LocalDateTime dateCreated) {
            if (dateCreated == null) {
                throw new IllegalArgumentException("Creation time can't be null");
            }
            Dish.this.dateCreated = dateCreated;
            return this;
        }

        public Dish build() {
            return Dish.this;
        }

    }
}
