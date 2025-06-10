package com.example.bfit.navdrawerfeatures.goals.domain.data

import com.example.bfit.util.Constants // Import your Constants
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("AndroidHeightPatternValidator")
class AndroidHeightPatternValidatorTest {

 private lateinit var validator: AndroidHeightPatternValidator

 @BeforeEach
 fun setUp() {
  validator = AndroidHeightPatternValidator()
 }

 @Nested
 @DisplayName("Given a valid height string format and within range")
 inner class ValidHeightFormatAndRange {

  @Test
  @DisplayName("When height is an integer within range (e.g., 170), then isValidHeight should return true")
  fun `integer height within range returns true`() {
   assertTrue(validator.isValidHeight("170"), "'170' should be valid.")
  }

  @Test
  @DisplayName("When height has one decimal place and is within range (e.g., 170.5), then isValidHeight should return true")
  fun `one decimal height within range returns true`() {
   assertTrue(validator.isValidHeight("170.5"), "'170.5' should be valid.")
  }

  @Test
  @DisplayName("When height is at the minimum allowed limit (e.g., ${Constants.MIN_HEIGHT}), then isValidHeight should return true")
  fun `minimum height returns true`() {
   assertTrue(validator.isValidHeight(Constants.MIN_HEIGHT.toString()), "Min height '${Constants.MIN_HEIGHT}' should be valid.")
  }

  @Test
  @DisplayName("When height is at the maximum allowed limit (e.g., ${Constants.MAX_HEIGHT}), then isValidHeight should return true")
  fun `maximum height returns true`() {
   assertTrue(validator.isValidHeight(Constants.MAX_HEIGHT.toString()), "Max height '${Constants.MAX_HEIGHT}' should be valid.")
  }

  @Test
  @DisplayName("When height is zero with one decimal place (e.g., 0.0) and within range, then isValidHeight should return true")
  fun `zero with one decimal place returns true if in range`() {
   // This test depends on 0.0 being within your MIN_HEIGHT and MAX_HEIGHT
   // If MIN_HEIGHT is > 0.0, this will fail, which is correct behavior.
   if (0.0f >= Constants.MIN_HEIGHT && 0.0f <= Constants.MAX_HEIGHT) {
    assertTrue(validator.isValidHeight("0.0"), "'0.0' should be valid if within range.")
   } else {
    assertFalse(validator.isValidHeight("0.0"), "'0.0' should be invalid if not within range.")
   }
  }

  @Test
  @DisplayName("When height is an integer zero (e.g., 0) and within range, then isValidHeight should return true")
  fun `integer zero returns true if in range`() {
   if (0.0f >= Constants.MIN_HEIGHT && 0.0f <= Constants.MAX_HEIGHT) {
    assertTrue(validator.isValidHeight("0"), "'0' should be valid if within range.")
   } else {
    assertFalse(validator.isValidHeight("0"), "'0' should be invalid if not within range.")
   }
  }
 }

 @Nested
 @DisplayName("Given height string format is invalid OR value is out of range")
 inner class InvalidHeightFormatOrRange {

  @ParameterizedTest(name = "When height has invalid format (e.g., \"{0}\"), then isValidHeight should return false")
  @ValueSource(strings = [
   "170.55",  // More than one decimal
   "abc",     // Non-numeric
   "170.",    // Trailing decimal without digit
   ".5",      // Leading decimal without digit
   "1 70",    // Space in between
   "170cm",   // Contains non-numeric characters
   "",        // Empty string
   "  ",      // Whitespace only
   "-170",    // Negative (also likely out of range)
   "+170"     // Leading plus (fails regex)
  ])
  fun `invalid format returns false`(invalidInput: String) {
   assertFalse(validator.isValidHeight(invalidInput), "Input '$invalidInput' has invalid format.")
  }

  @Test
  @DisplayName("When height format is valid but value is below minimum (e.g., ${Constants.MIN_HEIGHT - 0.1f}), then isValidHeight should return false")
  fun `height below minimum returns false`() {
   val belowMin = String.format("%.1f", Constants.MIN_HEIGHT - 0.1f) // Format to one decimal
   // Ensure the generated string is not accidentally valid due to extreme floating point values
   if ((Constants.MIN_HEIGHT - 0.1f) > 0) { // Only test if the value is positive, otherwise regex might fail first
    assertFalse(validator.isValidHeight(belowMin), "Height '$belowMin' (below min) should be invalid.")
   } else {
    assertFalse(validator.isValidHeight("0.0"), "Height '0.0' (below min if min >0) should be invalid.")
   }
  }

  @Test
  @DisplayName("When height format is valid but value is above maximum (e.g., ${Constants.MAX_HEIGHT + 0.1f}), then isValidHeight should return false")
  fun `height above maximum returns false`() {
   val aboveMax = String.format("%.1f", Constants.MAX_HEIGHT + 0.1f)
   assertFalse(validator.isValidHeight(aboveMax), "Height '$aboveMax' (above max) should be invalid.")
  }

  @Test
  @DisplayName("When height is a very large number string exceeding Float limits, then isValidHeight should return false")
  fun `very large number string returns false`() {
   // This will likely fail toFloatOrNull() before the range check.
   assertFalse(validator.isValidHeight("99999999999999999999999999999999999999999.9"))
  }
 }
}