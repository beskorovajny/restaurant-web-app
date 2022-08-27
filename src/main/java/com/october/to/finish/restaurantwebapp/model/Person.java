package com.october.to.finish.restaurantwebapp.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Person {
    private Set<Receipt> receipts;
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Role role;
    private CreditCard creditCard;
    private Address address;
    private char[] password;

    public static Builder newBuilder() {
        return new Person().new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public char[] getPassword() {
        return password;
    }

    public Set<Receipt> getOrders() {
        return receipts;
    }

    public void setOrders(Set<Receipt> receipts) {
        this.receipts = receipts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(receipts, person.receipts) && Objects.equals(email, person.email) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(phoneNumber, person.phoneNumber) && role == person.role && Objects.equals(creditCard, person.creditCard) && Objects.equals(address, person.address) && Arrays.equals(password, person.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(receipts, id, email, firstName, lastName, phoneNumber, role, creditCard, address);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "orders=" + receipts +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                ", creditCard=" + creditCard +
                ", address=" + address +
                ", password=" + Arrays.toString(password) +
                '}';
    }

    public enum Role {
        CLIENT("Client"),
        MANAGER("Manager"),
        UNAUTHORIZED_USER("Unauthorized_user");

        private final String roleName;

        Role(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleName() {
            return roleName;
        }
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(long id) {
            if (id < 0) {
                throw new IllegalArgumentException("ID can't be < 0");
            }
            Person.this.id = id;
            Person.this.receipts = new HashSet<>();
            return this;
        }

        public Builder setEmail(String email) {
            if (email == null) {
                throw new IllegalArgumentException("Email can't be null!");
            }
            Person.this.email = email;
            return this;
        }

        public Builder setFirstName(String firstName) {
            if (firstName == null) {
                throw new IllegalArgumentException("First name can't be null!");
            }
            Person.this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            if (lastName == null) {
                throw new IllegalArgumentException("Last name can't be null!");
            }
            Person.this.lastName = lastName;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            if (phoneNumber == null) {
                throw new IllegalArgumentException("Phone number can't be null!");
            }
            Person.this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setRole(Role role) {
            if (role == null) {
                throw new IllegalArgumentException("Role can't be null!");
            }
            Person.this.role = role;
            return this;
        }

        public Builder setCreditCard(CreditCard creditCard) {
            if (creditCard == null) {
                throw new IllegalArgumentException("Credit card can't be null!");
            }
            Person.this.creditCard = creditCard;
            return this;
        }

        public Builder setAddress(Address address) {
            if (address == null) {
                throw new IllegalArgumentException("Address can't be null!");
            }
            Person.this.address = address;
            return this;
        }

        public Builder setPassword(char[] password) {
            if (password == null) {
                throw new IllegalArgumentException("Password can't be null!");
            }
            Person.this.password = password;
            return this;
        }

        public Person build() {
            return Person.this;
        }
    }
}
