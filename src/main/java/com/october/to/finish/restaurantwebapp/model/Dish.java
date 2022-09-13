package com.october.to.finish.restaurantwebapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

public class Dish {
    private long id;
    private String title;
    private String description;
    private Category category;
    private double price;
    private int weightInGrams;
    private int count;
    private int minutesToCook;
    private LocalDateTime dateCreated;
    private byte[] image;

    public static Builder newBuilder() {
        return new Dish().new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getWeightInGrams() {
        return weightInGrams;
    }

    public int getCount() {
        return count;
    }

    public int getMinutesToCook() {
        return minutesToCook;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id == dish.id && Double.compare(dish.price, price) == 0 && weightInGrams == dish.weightInGrams && count == dish.count && minutesToCook == dish.minutesToCook && Objects.equals(title, dish.title) && Objects.equals(description, dish.description) && category == dish.category && Objects.equals(dateCreated, dish.dateCreated) && Arrays.equals(image, dish.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, description, category, price, weightInGrams, count, minutesToCook, dateCreated);
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
                ", weightInGrams=" + weightInGrams +
                ", count=" + count +
                ", minutesToCook=" + minutesToCook +
                ", dateCreated=" + dateCreated.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                ", image=" + Arrays.toString(image) +
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
            if (id < 0) {
                throw new IllegalArgumentException("ID can't be < 0!");
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

        public Builder setWeightInGrams(int weightInGrams) {
            if (weightInGrams < 1) {
                throw new IllegalArgumentException("Weight can't be < 0");
            }
            Dish.this.weightInGrams = weightInGrams;
            return this;
        }

        public Builder setCount(int count) {
            if (count < 1) {
                throw new IllegalArgumentException("Can't add less than one dish!");
            }
            Dish.this.count = count;
            return this;
        }

        public Builder setMinutesToCook(int minutes) {
            if (minutes < 0) {
                throw new IllegalArgumentException("Time can't be < 0");
            }
            Dish.this.minutesToCook = minutes;
            return this;
        }

        public Builder setDateCreated(LocalDateTime dateCreated) {
            if (dateCreated == null) {
                throw new IllegalArgumentException("Creation time can't be null");
            }
            Dish.this.dateCreated = dateCreated;
            return this;
        }

        public Builder setImage(byte[] image) {
            /*if (image == null) {
                throw new IllegalArgumentException("Image can't be null");
            }*/
            Dish.this.image = image;
            return this;
        }

        public Dish build() {
            return Dish.this;
        }

    }
}
