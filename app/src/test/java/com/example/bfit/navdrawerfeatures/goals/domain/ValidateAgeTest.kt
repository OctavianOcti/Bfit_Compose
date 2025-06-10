package com.example.bfit.navdrawerfeatures.goals.domain

import com.example.bfit.util.Constants
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("ValidateAge Use Case")
class ValidateAgeTest {

 private lateinit var validateAge: ValidateAge

 @BeforeEach
 fun setUp() {
  validateAge = ValidateAge()
 }

 @Nested
 @DisplayName("Given a valid age string")
 inner class ValidAgeScenarios {

  @Test
  @DisplayName("When age is within the defined range (e.g., 25), then execute() should return true")
  fun `age within range returns true`() {
   val age = "25"
   assertTrue(validateAge.execute(age), "Age '25' should be considered valid.")
  }

  @Test
  @DisplayName("When age is exactly the minimum allowed (e.g., ${Constants.MIN_AGE}), then execute() should return true")
  fun `minimum allowed age returns true`() {
   val age = Constants.MIN_AGE.toString()
   assertTrue(validateAge.execute(age), "Minimum age '${Constants.MIN_AGE}' should be valid.")
  }

  @Test
  @DisplayName("When age is exactly the maximum allowed (e.g., ${Constants.MAX_AGE}), then execute() should return true")
  fun `maximum allowed age returns true`() {
   val age = Constants.MAX_AGE.toString()
   assertTrue(validateAge.execute(age), "Maximum age '${Constants.MAX_AGE}' should be valid.")
  }

  @Test
  @DisplayName("When age string has leading or trailing whitespace but is otherwise valid (e.g., ' 30 '), then execute() should return true")
  fun `age with whitespace but valid number returns true`() {
   // Kotlin's toIntOrNull() trims whitespace by default.
   val age = " 30 "
   assertTrue(validateAge.execute(age), "Age ' 30 ' should be valid after trimming.")
  }
 }

 @Nested
 @DisplayName("Given an invalid age string")
 inner class InvalidAgeScenarios {

  @Test
  @DisplayName("When age is below the minimum allowed (e.g., ${Constants.MIN_AGE - 1}), then execute() should return false")
  fun `age below minimum returns false`() {
   val age = (Constants.MIN_AGE - 1).toString()
   assertFalse(validateAge.execute(age), "Age '${Constants.MIN_AGE - 1}' should be invalid.")
  }

  @Test
  @DisplayName("When age is above the maximum allowed (e.g., ${Constants.MAX_AGE + 1}), then execute() should return false")
  fun `age above maximum returns false`() {
   val age = (Constants.MAX_AGE + 1).toString()
   assertFalse(validateAge.execute(age), "Age '${Constants.MAX_AGE + 1}' should be invalid.")
  }

  @ParameterizedTest(name = "When age is non-numeric (e.g., \"{0}\"), then execute() should return false")
  @ValueSource(strings = ["abc", "twenty-five", "18a", "??", "-50", "+50"]) // Added more varied non-numeric cases
  fun `non-numeric age returns false`(invalidAgeInput: String) {
   assertFalse(validateAge.execute(invalidAgeInput), "Non-numeric input '$invalidAgeInput' should be invalid.")
  }

  @Test
  @DisplayName("When age is an empty string, then execute() should return false")
  fun `empty string age returns false`() {
   val age = ""
   assertFalse(validateAge.execute(age), "Empty string for age should be invalid.")
  }

  @Test
  @DisplayName("When age contains decimal points (e.g., 25.5), then execute() should return false")
  fun `decimal age returns false`() {
   val age = "25.5"
   assertFalse(validateAge.execute(age), "Age with decimals '25.5' should be invalid.")
  }

  @Test
  @DisplayName("When age is a very large number string exceeding Int limits, then execute() should return false")
  fun `very large number string returns false`() {
   val age = "99999999999999999999"
   assertFalse(validateAge.execute(age), "Extremely large number string for age should be invalid.")
  }

  @Test
  @DisplayName("When age string is just whitespace, then execute() should return false")
  fun `whitespace only string returns false`() {
   val age = "   "
   assertFalse(validateAge.execute(age), "Whitespace-only string for age should be invalid.")
  }
 }
}