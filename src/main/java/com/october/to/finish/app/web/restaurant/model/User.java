package com.october.to.finish.app.web.restaurant.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private char[] password;
    private Set<Receipt> receipts;

    public static Builder newBuilder() {
        return new User().new Builder();
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

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role can't be null");
        }
        this.role = role;
    }

    public char[] getPassword() {
        return password;
    }

    public Set<Receipt> getOrders() {
        return receipts;
    }

    public void setOrders(Set<Receipt> orders) {
        if (orders == null) {
            throw new IllegalArgumentException();
        }
        this.receipts = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && role == user.role && Arrays.equals(password, user.password) && Objects.equals(receipts, user.receipts);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, email, firstName, lastName, role, receipts);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", password=" + String.valueOf(password) +
                ", receipts=" + receipts +
                '}';
    }

    public enum Role {
        CLIENT(1, "Client"),
        MANAGER(2, "Manager");
        private final int id;
        private final String roleName;

        Role(int id, String roleName) {
            this.id = id;
            this.roleName = roleName;
        }

        public int getId() {
            return id;
        }

        public String getRoleName() {
            return roleName;
        }
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(long id) {
            if (id < 1) {
                throw new IllegalArgumentException("ID can't be < 1");
            }
            User.this.id = id;
            User.this.receipts = new HashSet<>();
            return this;
        }

        public Builder setEmail(String email) {
            if (email == null) {
                throw new IllegalArgumentException("Email can't be null!");
            }
            User.this.email = email;
            return this;
        }

        public Builder setFirstName(String firstName) {
            if (firstName == null) {
                throw new IllegalArgumentException("First name can't be null!");
            }
            User.this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            if (lastName == null) {
                throw new IllegalArgumentException("Last name can't be null!");
            }
            User.this.lastName = lastName;
            return this;
        }

        public Builder setRole(User.Role role) {
            if (role == null) {
                throw new IllegalArgumentException("Role can't be null!");
            }
            User.this.role = role;
            return this;
        }
        public Builder setPassword(char[] password) {
            if (password == null) {
                throw new IllegalArgumentException("Password can't be null!");
            }
            User.this.password = password;
            return this;
        }

        public User build() {
            return User.this;
        }
    }
}
