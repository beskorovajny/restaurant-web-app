package com.october.to.finish.app.web.restaurant.utils.validation;

import com.october.to.finish.app.web.restaurant.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    private static final String EMAIL_PATTERN = "^(?=.{4,45}$)([\\w-\\.]{1,})+@([\\w-]+\\.)+([\\w-]{2,4})$";
    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,65}$";
    private static final String INPUT_TEXT_PATTERN = "^\\p{L}{2,45}$";
    private static final String PHONE_PATTERN = "^(?=.{3,45}$)[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
    private static final String CARD_NUMBER_PATTERN = "\\b\\d{13,16}\\b";

    public boolean validateUser(User user) {
        return validateTextInput(user.getFirstName()) && validateTextInput(user.getLastName())
                && validateEmail(user.getEmail()) && validatePhoneNumber(user.getPhoneNumber())
                && validatePassword(String.valueOf(user.getPassword()));
    }




    public boolean validatePassword(String password) {
        Matcher matcher = Pattern.compile(PASSWORD_PATTERN).matcher(password);
        return password.isEmpty() || (matcher.matches() && !password.trim().isEmpty());
    }

    public boolean validateEmail(String email) {
        Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(email);
        return email.isEmpty() || (matcher.matches() && !email.trim().isEmpty());
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        Matcher matcher = Pattern.compile(PHONE_PATTERN).matcher(phoneNumber);
        return phoneNumber.isEmpty() || (matcher.matches() && !phoneNumber.trim().isEmpty());
    }

    public boolean validateTextInput(String input) {
        Matcher matcher = Pattern.compile(INPUT_TEXT_PATTERN).matcher(input);
        return input.isEmpty() || (matcher.matches() && !input.trim().isEmpty());
    }

    public boolean validateCardNumber(String cardNumber) {
        Matcher matcher = Pattern.compile(CARD_NUMBER_PATTERN).matcher(cardNumber);
        return cardNumber.isEmpty() || (matcher.matches() && !cardNumber.trim().isEmpty());
    }

}
