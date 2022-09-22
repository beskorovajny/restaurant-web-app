package com.october.to.finish.app.web.restaurant.model;

import java.util.Arrays;
import java.util.Objects;

public class CreditCard {
    private String cardNumber;
    private String bankName;
    private double balance;
    private char[] password;

    public CreditCard() {
    }

    public CreditCard(String bankName, String cardNumber, double balance, char[] password) {
        if (bankName == null || cardNumber == null || balance < 0 || password == null) {
            throw new IllegalArgumentException("Can't create credit card with given parameters...");
        }
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.password = password;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        if (bankName == null || bankName.isEmpty()) {
            throw new IllegalArgumentException("Bank name can't be empty or null");
        }
        this.bankName = bankName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Card number can't be empty or null.");
        }
        this.cardNumber = cardNumber;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance can't be < 0!");
        }
        this.balance = balance;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        if (password == null) {
            throw new IllegalArgumentException("Password can`t be null!");
        }
        this.password = password;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Double.compare(that.balance, balance) == 0 && Objects.equals(bankName, that.bankName) && Objects.equals(cardNumber, that.cardNumber) && Arrays.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(bankName, cardNumber, balance);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "bankName='" + bankName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", balance=" + balance +
                ", password=" + String.valueOf(password) +
                '}';
    }
}