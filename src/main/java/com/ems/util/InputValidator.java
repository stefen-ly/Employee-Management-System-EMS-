
package com.ems.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class InputValidator {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.trim().matches("^[a-zA-Z\\s'-]+$");
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(EMAIL_REGEX, email.trim());
    }

    public static String processEmail(String email) {
        if (email != null && !email.contains("@")) {
            return email.trim() + "@gmail.com";
        }
        return email;
    }

    public static boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return phone.trim().matches("^\\+?\\d{9,10}$");
    }

    public static boolean isValidAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            return false;
        }
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        return age >= 18 && age <= 120;
    }

    public static int calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return 0;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public static boolean isValidSalary(double salary) {
        return salary > 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}