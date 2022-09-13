package com.october.to.finish.restaurantwebapp.validation;

public class ValidationUtils {
    public static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    public static final String NAME_PATTERN = "\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+";
    public static final String PHONE_PATTERN = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";

}
