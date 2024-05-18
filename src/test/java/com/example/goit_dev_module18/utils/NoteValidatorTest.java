package com.example.goit_dev_module18.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteValidatorTest {
    private NoteValidator noteValidator = new NoteValidator();

    @Test
    void titleIsValidTest() {
        Assertions.assertEquals(true, noteValidator.titleIsValid("Title for note"));
    }
    @Test
    void titleIsBlankTest() {
        Assertions.assertEquals(false, noteValidator.titleIsValid(""));
        Assertions.assertEquals(false, noteValidator.titleIsValid("    "));
        Assertions.assertEquals(false, noteValidator.titleIsValid("\t\t"));
    }
    @Test
    void titleIsNotValidTest() {
        Assertions.assertEquals(false, noteValidator.titleIsValid("Ti"));
    }
    @Test
    void contentIsValidTest() {
        Assertions.assertEquals(true, noteValidator.contentIsValid("Content for note"));
    }
    @Test
    void contentIsBlankTest() {
        Assertions.assertEquals(false, noteValidator.contentIsValid(""));
        Assertions.assertEquals(false, noteValidator.contentIsValid("   "));
        Assertions.assertEquals(false, noteValidator.contentIsValid("\t\t\n"));
    }
    @Test
    void contentIsNotValidTest() {
        Assertions.assertEquals(false, noteValidator.contentIsValid("Wow"));
    }
}