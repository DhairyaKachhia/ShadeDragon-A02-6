package com.example.skylink.UnitTestStub;

import static org.junit.Assert.*;

import com.example.skylink.business.validations.IValidateUserAuth;
import com.example.skylink.business.validations.ValidateUserAuth;

import org.junit.Before;
import org.junit.Test;

public class ValidateUserAuthUnit {

    private IValidateUserAuth validator;

    @Before
    public void setup() {
        validator = new ValidateUserAuth();
    }

    @Test
    public void validEmail_emptyEmail_returnsErrorMessage() {
        String error = validator.validEmail("");        // empty email field
        assertEquals("Email cannot be empty", error);
    }

    @Test
    public void validEmail_invalidEmail_returnsErrorMessage() {
        String error = validator.validEmail("invalidemail");        // invalid format
        assertEquals("Invalid email", error);
    }

    @Test
    public void validEmail_validEmail() {
        String error = validator.validEmail("validemail@example.com");      // valid email
        assertEquals("", error);
    }

    @Test
    public void validPassword_emptyPassword_returnsErrorMessage() {
        String error = validator.validPassword("");         // empty password field
        assertEquals("Password cannot be empty", error);
    }

    @Test
    public void validPassword_nonEmptyPassword_returnsEmptyString() {
        String error = validator.validPassword("password123");      // valid password
        assertEquals("", error);
    }

    @Test
    public void validRePassword_emptyRePassword_returnsErrorMessage() {
        String error = validator.validRePassword("password", "");    // empty reEnter password field
        assertEquals("Password cannot be empty", error);
    }

    @Test
    public void validRePassword_nonMatchingPasswords_returnsErrorMessage() {
        String error = validator.validRePassword("password", "differentpassword");      // password mismatch
        assertEquals("Password do not match", error);
    }

    @Test
    public void validRePassword_matchingPasswords_returnsEmptyString() {
        String error = validator.validRePassword("password", "password");   // password matches
        assertEquals("", error);
    }

    @Test
    public void validFullname_emptyName_returnsErrorMessage() {
        String error = validator.validFullname("");         // empty fullname field
        assertEquals("Name cannot be empty", error);
    }

    @Test
    public void validFullname_nonEmptyName_returnsEmptyString() {
        String error = validator.validFullname("Hello world");     // valid fullname
        assertEquals("", error);
    }

    @Test
    public void validAddress_Success() {
        String error = validator.validAddress("123 Some rd.");     // valid address
        assertEquals("", error);
    }

    @Test
    public void validAddress_Fail() {
        String error = validator.validAddress("");     // empty address
        assertEquals("Address cannot be empty", error);
    }

    @Test
    public void validCity_Success() {
        String error = validator.validCity("Winnipeg");     // valid city
        assertEquals("", error);
    }

    @Test
    public void validCity_Fail() {
        String error = validator.validCity("");     // empty city
        assertEquals("City cannot be empty", error);
    }

    @Test
    public void validProvince_Success() {
        String error = validator.validProvince("MB");     // valid province
        assertEquals("", error);
    }

    @Test
    public void validProvince_Fail() {
        String error = validator.validProvince("");     // empty province
        assertEquals("Province cannot be empty", error);
    }

    @Test
    public void validPhone_Success() {
        String error = validator.validPhone("1234567890");     // valid phone
        assertEquals("", error);
    }

    @Test
    public void validPhone_Fail() {
        String error = validator.validPhone("");     // empty phone
        assertEquals("Phone number cannot be empty", error);
    }

    @Test
    public void validPhone_InvalidNumber() {
        String error = validator.validPhone("12345");     // phone number is not 10 digit
        assertEquals("Invalid phone number", error);
    }

    @Test
    public void validDOB_Success() {
        String error = validator.validDOB("12/20/2000");     // valid date of birth
        assertEquals("", error);
    }

    @Test
    public void validDOB_Fail() {
        String error = validator.validDOB("");     // empty date of birth
        assertEquals("Date of birth cannot be empty", error);
    }

    @Test
    public void validGender_Success() {
        String error = validator.validGender("Male");     // valid gender
        assertEquals("", error);
    }

    @Test
    public void validGender_Fail() {
        String error = validator.validGender("");     // empty gender
        assertEquals("Gender cannot be empty", error);
    }
}