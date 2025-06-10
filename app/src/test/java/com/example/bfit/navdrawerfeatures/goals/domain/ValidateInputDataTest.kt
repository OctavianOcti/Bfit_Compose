package com.example.bfit.navdrawerfeatures.goals.domain

import android.content.Context
import com.example.bfit.R
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@DisplayName("ValidateInputData Use Case")
@ExtendWith(MockitoExtension::class) // Initializes Mockito mocks (like @Mock context)
class ValidateInputDataTest {

 @Mock
 private lateinit var mockContext: Context // Mock the Android Context

 private lateinit var validateInputData: ValidateInputData

 // Define the placeholder strings that your app uses.
 // It's good practice to keep these consistent with what getString would return.
 private val placeholderGender = "Select Gender"
 private val placeholderWeight = "Set Current Weight"
 private val placeholderHeight = "Set Current Height"
 private val placeholderAge = "Set Current Age"
 private val placeholderActivityLevel = "Set Current Activity Level"
 private val placeholderGoal = "Set Your Goal"

 @BeforeEach
 fun setUp() {
  validateInputData = ValidateInputData()

  // Configure the mockContext to return specific strings when getString() is called
  // This simulates the behavior of retrieving strings from resources.
  whenever(mockContext.getString(R.string.set_gender)).thenReturn(placeholderGender)
  whenever(mockContext.getString(R.string.set_current_weight)).thenReturn(placeholderWeight)
  whenever(mockContext.getString(R.string.set_current_height)).thenReturn(placeholderHeight)
  whenever(mockContext.getString(R.string.set_current_age)).thenReturn(placeholderAge)
  whenever(mockContext.getString(R.string.set_current_activity_level)).thenReturn(placeholderActivityLevel)
  whenever(mockContext.getString(R.string.set_your_goal)).thenReturn(placeholderGoal)
 }

 private fun createValidInputMap(): Map<String, String> {
  return mapOf(
   "gender" to "Male",
   "weight" to "75",
   "height" to "180",
   "age" to "30",
   "activityLevel" to "Moderately Active",
   "goal" to "Lose Weight"
  )
 }

 @Nested
 @DisplayName("Given all inputs are valid and not placeholders")
 inner class AllValidInputs {
  @Test
  @DisplayName("then execute() should return true")
  fun `all valid non-placeholder inputs returns true`() {
   val inputs = createValidInputMap()
   val result = validateInputData.execute(
    context = mockContext,
    gender = inputs["gender"]!!,
    weight = inputs["weight"]!!,
    height = inputs["height"]!!,
    age = inputs["age"]!!,
    activityLevel = inputs["activityLevel"]!!,
    goal = inputs["goal"]!!
   )
   assertTrue(result, "Expected true when all inputs are valid and not placeholders")
  }
 }

 @Nested
 @DisplayName("Given one or more inputs are empty")
 inner class EmptyInputs {
  private val fields = listOf("gender", "weight", "height", "age", "activityLevel", "goal")

  @Test
  @DisplayName("then execute() should return false if gender is empty")
  fun `empty gender returns false`() {
   testWithOneFieldModified(fieldToModify = "gender", newValue = "")
  }

  @Test
  @DisplayName("then execute() should return false if weight is empty")
  fun `empty weight returns false`() {
   testWithOneFieldModified(fieldToModify = "weight", newValue = "")
  }

  @Test
  @DisplayName("then execute() should return false if height is empty")
  fun `empty height returns false`() {
   testWithOneFieldModified(fieldToModify = "height", newValue = "")
  }

  @Test
  @DisplayName("then execute() should return false if age is empty")
  fun `empty age returns false`() {
   testWithOneFieldModified(fieldToModify = "age", newValue = "")
  }

  @Test
  @DisplayName("then execute() should return false if activityLevel is empty")
  fun `empty activityLevel returns false`() {
   testWithOneFieldModified(fieldToModify = "activityLevel", newValue = "")
  }

  @Test
  @DisplayName("then execute() should return false if goal is empty")
  fun `empty goal returns false`() {
   testWithOneFieldModified(fieldToModify = "goal", newValue = "")
  }

  // Helper method to reduce boilerplate for empty/placeholder tests
  private fun testWithOneFieldModified(fieldToModify: String, newValue: String) {
   val inputs = createValidInputMap().toMutableMap()
   inputs[fieldToModify] = newValue

   val result = validateInputData.execute(
    context = mockContext,
    gender = inputs["gender"]!!,
    weight = inputs["weight"]!!,
    height = inputs["height"]!!,
    age = inputs["age"]!!,
    activityLevel = inputs["activityLevel"]!!,
    goal = inputs["goal"]!!
   )
   assertFalse(result, "Expected false when '$fieldToModify' is '$newValue'")
  }
 }


 @Nested
 @DisplayName("Given one or more inputs are placeholder strings")
 inner class PlaceholderInputs {

  @Test
  @DisplayName("then execute() should return false if gender is the placeholder")
  fun `placeholder gender returns false`() {
   testWithOneFieldAsPlaceholder(fieldToModify = "gender", placeholderValue = placeholderGender)
  }

  @Test
  @DisplayName("then execute() should return false if weight is the placeholder")
  fun `placeholder weight returns false`() {
   testWithOneFieldAsPlaceholder(fieldToModify = "weight", placeholderValue = placeholderWeight)
  }

  @Test
  @DisplayName("then execute() should return false if height is the placeholder")
  fun `placeholder height returns false`() {
   testWithOneFieldAsPlaceholder(fieldToModify = "height", placeholderValue = placeholderHeight)
  }

  @Test
  @DisplayName("then execute() should return false if age is the placeholder")
  fun `placeholder age returns false`() {
   testWithOneFieldAsPlaceholder(fieldToModify = "age", placeholderValue = placeholderAge)
  }

  @Test
  @DisplayName("then execute() should return false if activityLevel is the placeholder")
  fun `placeholder activityLevel returns false`() {
   testWithOneFieldAsPlaceholder(fieldToModify = "activityLevel", placeholderValue = placeholderActivityLevel)
  }

  @Test
  @DisplayName("then execute() should return false if goal is the placeholder")
  fun `placeholder goal returns false`() {
   testWithOneFieldAsPlaceholder(fieldToModify = "goal", placeholderValue = placeholderGoal)
  }

  // Helper method, similar to the one for empty inputs
  private fun testWithOneFieldAsPlaceholder(fieldToModify: String, placeholderValue: String) {
   val inputs = createValidInputMap().toMutableMap()
   inputs[fieldToModify] = placeholderValue

   val result = validateInputData.execute(
    context = mockContext,
    gender = inputs["gender"]!!,
    weight = inputs["weight"]!!,
    height = inputs["height"]!!,
    age = inputs["age"]!!,
    activityLevel = inputs["activityLevel"]!!,
    goal = inputs["goal"]!!
   )
   assertFalse(result, "Expected false when '$fieldToModify' is its placeholder '$placeholderValue'")
  }
 }
}