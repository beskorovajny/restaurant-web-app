package com.october.to.finish.app.web.restaurant.utils.validation;

import com.october.to.finish.app.web.restaurant.model.Contacts;
import com.october.to.finish.app.web.restaurant.model.Dish;
import com.october.to.finish.app.web.restaurant.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

public class ValidationUtils {
    private static final String EMAIL_PATTERN = "^(?=.{4,45}$)([\\w-\\.]{1,})+@([\\w-]+\\.)+([\\w-]{2,4})$";
    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,65}$";
    private static final String SHA_256_PATTERN = "^[a-fA-F0-9]{64}$";
    private static final String INPUT_USER_FIELD_PATTERN = "(?=.{1,45}$)[\\p{L}\\s*]+$";
    private static final String PHONE_PATTERN = "^(?=.{3,45}$)[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
    private static final String PRICE_PATTERN = "^\\d+(.\\d{1,2})?$";
    private static final String INTEGER_PATTERN = "^\\d+$";
    private static final String REGULAR_TEXT_PATTERN_45 = "(?=.{1,45}$)[\\p{L}\\s*]+$";
    private static final String LONG_TEXT_PATTERN_255 = "(?=.{1,255}$)[\\p{L}\\s*]+$";
    private static final String STREET_BUILDING_PATTERN = "(?=.{1,45}$)[\\p{L}\\s\\d*]+$";

    private ValidationUtils() {
    }

    public static boolean validateUser(User user) {
        return nonNull(user) && validateUserFieldInput(user.getFirstName()) && validateUserFieldInput(user.getLastName())
                && validateEmail(user.getEmail()) && validatePasswordSHA256(String.valueOf(user.getPassword()));
    }

    public static boolean validateDish(Dish dish) {
        return nonNull(dish) && validateRegularTextInput(dish.getTitle()) && validateLongTextInput(dish.getDescription())
                && validatePrice(dish.getPrice()) && validateInteger(dish.getWeight()) &&
                validateInteger(dish.getCooking());
    }

    public static boolean validateContacts(Contacts contacts) {
        return nonNull(contacts) && validateRegularTextInput(contacts.getCountry()) &&
                validateRegularTextInput(contacts.getCity()) && validateStreetOrBuilding(contacts.getStreet())
                && validateStreetOrBuilding(contacts.getBuildingNumber()) && validatePhoneNumber(contacts.getPhone());
    }

    public static boolean validatePassword(String input) {
        Matcher matcher = Pattern.compile(PASSWORD_PATTERN).matcher(input);
        return matcher.matches();
    }

    public static boolean validatePasswordSHA256(String input) {
        Matcher matcher = Pattern.compile(SHA_256_PATTERN).matcher(input);
        return matcher.matches();
    }

    public static boolean validateEmail(String email) {
        Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(email);
        return matcher.matches();
    }

    public static boolean validatePhoneNumber(String input) {
        Matcher matcher = Pattern.compile(PHONE_PATTERN).matcher(input);
        return matcher.matches();
    }

    public static boolean validateUserFieldInput(String input) {
        Matcher matcher = Pattern.compile(INPUT_USER_FIELD_PATTERN).matcher(input);
        return matcher.matches();
    }

    public static boolean validatePrice(Double input) {
        Matcher matcher = Pattern.compile(PRICE_PATTERN).matcher(String.valueOf(input));
        return matcher.matches();
    }

    public static boolean validateRegularTextInput(String input) {
        Matcher matcher = Pattern.compile(REGULAR_TEXT_PATTERN_45).matcher(input);
        return matcher.matches();
    }

    public static boolean validateLongTextInput(String input) {
        Matcher matcher = Pattern.compile(LONG_TEXT_PATTERN_255).matcher(input);
        return matcher.matches();
    }

    public static boolean validateInteger(Integer input) {
        Matcher matcher = Pattern.compile(INTEGER_PATTERN).matcher(String.valueOf(input));
        return matcher.matches();
    }

    public static boolean validateStreetOrBuilding(String input) {
        Matcher matcher = Pattern.compile(STREET_BUILDING_PATTERN).matcher(input);
        return matcher.matches();
    }
}
