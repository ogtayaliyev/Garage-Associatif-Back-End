package com.example.garage2.securite;

public class PasswordValidator {

    public boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean containsDigit = false;
        boolean containsLowerCase = false;
        boolean containsUpperCase = false;
        boolean containsSpecialCharacter = false;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true;
            } else if (Character.isLowerCase(c)) {
                containsLowerCase = true;
            } else if (Character.isUpperCase(c)) {
                containsUpperCase = true;
            } else if ("!@#$%^&*()-_=+\\|[{]};:'\",<.>/?".contains(String.valueOf(c))) {
                containsSpecialCharacter = true;
            }
        }

        return containsDigit && containsLowerCase && containsUpperCase && containsSpecialCharacter;
    }
}


