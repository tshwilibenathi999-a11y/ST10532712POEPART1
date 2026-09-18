package com.mycompany.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the validation, login, and status-message methods in Main.java.
 *
 * Note: this test class is placed in the same package (com.mycompany.main) so it
 * can set the package-private static fields (registeredUsername, registeredPassword)
 * directly, instead of going through registerUser() which reads from System.in.
 */
class MainTest {

    @BeforeEach
    void resetState() {
        // Reset registered details before each test so tests don't affect each other
        Main.registeredUsername = null;
        Main.registeredPassword = null;
        Main.registeredCellPhone = null;
    }

    // ---------- checkUserName ----------

    @Test
    void checkUserName_validUsernameWithUnderscore_returnsTrue() {
        // Exactly 5 characters, contains an underscore
        assertTrue(Main.checkUserName("kt_za"));
    }

    @Test
    void checkUserName_tooShort_returnsFalse() {
        assertFalse(Main.checkUserName("kt_z"));
    }

    @Test
    void checkUserName_tooLong_returnsFalse() {
        assertFalse(Main.checkUserName("kt_zab"));
    }

    @Test
    void checkUserName_noUnderscore_returnsFalse() {
        // Exactly 5 characters, but no underscore
        assertFalse(Main.checkUserName("ktzab"));
    }

    @Test
    void checkUserName_emptyString_returnsFalse() {
        assertFalse(Main.checkUserName(""));
    }

    // ---------- checkPasswordComplexity ----------

    @Test
    void checkPasswordComplexity_validPassword_returnsTrue() {
        // >= 8 chars, upper, lower, digit, special character
        assertTrue(Main.checkPasswordComplexity("Passw0rd!"));
    }

    @Test
    void checkPasswordComplexity_tooShort_returnsFalse() {
        assertFalse(Main.checkPasswordComplexity("P0rd!"));
    }

    @Test
    void checkPasswordComplexity_missingUppercase_returnsFalse() {
        assertFalse(Main.checkPasswordComplexity("passw0rd!"));
    }

    @Test
    void checkPasswordComplexity_missingLowercase_returnsFalse() {
        assertFalse(Main.checkPasswordComplexity("PASSW0RD!"));
    }

    @Test
    void checkPasswordComplexity_missingDigit_returnsFalse() {
        assertFalse(Main.checkPasswordComplexity("Password!"));
    }

    @Test
    void checkPasswordComplexity_missingSpecialCharacter_returnsFalse() {
        assertFalse(Main.checkPasswordComplexity("Passw0rd"));
    }

    // ---------- checkCellPhoneNumber ----------

    @Test
    void checkCellPhoneNumber_validNumber_returnsTrue() {
        // +27 followed by exactly 9 digits
        assertTrue(Main.checkCellPhoneNumber("+27821234567"));
    }

    @Test
    void checkCellPhoneNumber_missingPlus_returnsFalse() {
        assertFalse(Main.checkCellPhoneNumber("27821234567"));
    }

    @Test
    void checkCellPhoneNumber_wrongCountryCode_returnsFalse() {
        assertFalse(Main.checkCellPhoneNumber("+44821234567"));
    }

    @Test
    void checkCellPhoneNumber_tooFewDigits_returnsFalse() {
        assertFalse(Main.checkCellPhoneNumber("+2782123456"));
    }

    @Test
    void checkCellPhoneNumber_tooManyDigits_returnsFalse() {
        assertFalse(Main.checkCellPhoneNumber("+2782123456789"));
    }

    @Test
    void checkCellPhoneNumber_containsLetters_returnsFalse() {
        assertFalse(Main.checkCellPhoneNumber("+2782abc4567"));
    }

    // ---------- loginUser ----------

    @Test
    void loginUser_correctCredentials_returnsTrue() {
        Main.registeredUsername = "kt_za";
        Main.registeredPassword = "Passw0rd!";

        assertTrue(Main.loginUser("kt_za", "Passw0rd!"));
    }

    @Test
    void loginUser_wrongUsername_returnsFalse() {
        Main.registeredUsername = "kt_za";
        Main.registeredPassword = "Passw0rd!";

        assertFalse(Main.loginUser("wrong", "Passw0rd!"));
    }

    @Test
    void loginUser_wrongPassword_returnsFalse() {
        Main.registeredUsername = "kt_za";
        Main.registeredPassword = "Passw0rd!";

        assertFalse(Main.loginUser("kt_za", "WrongPass1!"));
    }

    @Test
    void loginUser_noUserRegistered_returnsFalse() {
        // registeredUsername/registeredPassword are null (see resetState)
        assertFalse(Main.loginUser("kt_za", "Passw0rd!"));
    }

    // ---------- returnLoginStatus ----------

    @Test
    void returnLoginStatus_successful_returnsWelcomeMessage() {
        assertEquals(
                "Welcome, it is great to see you again.",
                Main.returnLoginStatus(true)
        );
    }

    @Test
    void returnLoginStatus_unsuccessful_returnsErrorMessage() {
        assertEquals(
                "Username or password incorrect, please try again.",
                Main.returnLoginStatus(false)
        );
    }
}
