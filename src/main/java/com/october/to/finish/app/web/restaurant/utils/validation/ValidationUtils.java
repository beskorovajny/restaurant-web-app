package com.october.to.finish.app.web.restaurant.utils.validation;

import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    private static final String EMAIL_PATTERN = "^(?=.{4,45}$)([\\w-\\.]{1,})+@([\\w-]+\\.)+([\\w-]{2,4})$";
    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,65}$";
    private static final String INPUT_USER_PATTERN = "^\\p{L}{2,45}$";
    private static final String PHONE_PATTERN = "^(?=.{3,45}$)[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
    private static final String DECIMAL_PATTERN = "^\\d+(.\\d{1,2})?$";
    private static final String INTEGER_PATTERN = "^\\d+$";
    private static final String REGULAR_TEXT_PATTERN = "^(?=.{3,45}$)\\w+( \\w+)*$";
    private static final String LONG_TEXT_PATTERN = "^(?=.{3,255}$)\\w+( \\w+)*$";


    public boolean validateUser(User user) {
        return validateTextInput(user.getFirstName()) && validateTextInput(user.getLastName())
                && validateEmail(user.getEmail()) && validatePhoneNumber(user.getPhoneNumber())
                && validatePassword(String.valueOf(user.getPassword()));
    }
    public boolean validateDish(Dish dish) {
        return false;
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
        Matcher matcher = Pattern.compile(INPUT_USER_PATTERN).matcher(input);
        return input.isEmpty() || (matcher.matches() && !input.trim().isEmpty());
    }

    public boolean validatePrice(double price) {
        Matcher matcher = Pattern.compile(DECIMAL_PATTERN).matcher(String.valueOf(price));
        return String.valueOf(price).isEmpty() || (matcher.matches() && !String.valueOf(price).trim().isEmpty());
    }

}
