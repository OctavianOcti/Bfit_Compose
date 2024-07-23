package com.example.bfit.main.domain.model

/*
(carbohydrates/protein/fats)
Weight loss: 40/40/20
Weight gain: 40/30/30
Weight maintenance: 40/30/30
 */

class UserInfo {
    private var gender = ""
    private var activityLevel = ""
    private var goal = ""
    private var age = 0
    private var BMR = 0
    private var calories = 0
    private var weight = 0.0f
    private var height = 0.0f
    private var protein = 0
    private var fat = 0
    private var carb = 0
    private var proteinPercentage = 0.0f
    private var carbPercentage = 0.0f
    private var fatPercentage = 0.0f

    fun isEmpty(): Boolean {
        return gender.isEmpty() && activityLevel.isEmpty() && goal.isEmpty()
                && age == 0 && BMR == 0 && calories == 0 && weight == 0.0f
                && height == 0.0f && protein == 0 && fat == 0 && carb == 0
                && proteinPercentage == 0.0f && carbPercentage == 0.0f && fatPercentage == 0.0f
    }

    constructor()

    constructor(
        gender: String,
        activityLevel: String,
        goal: String,
        age: Int,
        weight: Float,
        height: Float
    ) {
        this.gender = gender
        this.activityLevel = activityLevel
        this.goal = goal
        this.age = age
        this.weight = weight
        this.height = height
        this.calories = 0
        this.BMR = 0
        countCalories()
    }

    constructor(
        gender: String,
        activityLevel: String,
        goal: String,
        age: Int,
        weight: Float,
        height: Float,
        protein: Int,
        carb:Int,
        fat:Int,
        proteinPercentage: Float,
        carbPercentage: Float,
        fatPercentage: Float,
        calories:Int,
    ){
        this.gender = gender
        this.activityLevel = activityLevel
        this.goal = goal
        this.age = age
        this.weight = weight
        this.height = height
        this.protein=protein
        this.carb=carb
        this.fat=fat
        this.proteinPercentage=proteinPercentage
        this.carbPercentage=carbPercentage
        this.fatPercentage=fatPercentage
        this.calories = calories
        determineBMR()
    }

    private fun determineBMR() {
        BMR = when (gender) {
            "Male" -> (10 * weight + 6.25 * height - 5 * age + 5).toInt()
            "Female" -> (10 * weight + 6.25 * height - 5 * age - 161).toInt()
            else -> 0
        }
        calories = BMR
    }

    private fun countCalories() {
        determineBMR()
        calories *= when (activityLevel) {
            "Sedentary" -> 1.2.toInt()
            "Slightly active" -> 1.375.toInt()
            "Moderately active" -> 1.55.toInt()
            "Very active" -> 1.725.toInt()
            "Extremely active" -> 1.9.toInt()
            else -> 1.0.toInt()
        }
        when (goal) {
            "Lose 1kg per week" -> {
                calories -= 1100
                proteinPercentage = 40f
                carbPercentage = 40f
                fatPercentage = 20f
                setMacros()
            }

            "Lose 0.75kg per week" -> {
                calories -= 825
                proteinPercentage = 40f
                carbPercentage = 40f
                fatPercentage = 20f
                setMacros()
            }

            "Lose 0.5kg per week" -> {
                calories -= 550
                proteinPercentage = 40f
                carbPercentage = 40f
                fatPercentage = 20f
                setMacros()
            }

            "Maintain current weight" -> {
                proteinPercentage = 30f
                fatPercentage = 30f
                carbPercentage = 40f
                setMacros()
            }

            "Gain 0.25kg per week" -> {
                calories += 275
                proteinPercentage = 30f
                fatPercentage = 30f
                carbPercentage = 40f
                setMacros()
            }

            "Gain 0.5kg per week" -> {
                calories += 550
                proteinPercentage = 30f
                fatPercentage = 30f
                carbPercentage = 40f
                setMacros()
            }
        }
    }

    private fun setMacros() {
        protein = (calories * proteinPercentage / 400).toInt()
        carb = (calories * carbPercentage / 400).toInt()
        fat = (calories * fatPercentage / 900).toInt()
    }

    override fun toString(): String {
        return "UserInfo{" +
                "gender='$gender', " +
                "activityLevel='$activityLevel', " +
                "goal='$goal', " +
                "age=$age, " +
                "BMR=$BMR, " +
                "calories=$calories, " +
                "weight=$weight, " +
                "height=$height, " +
                "protein=$protein, " +
                "fat=$fat, " +
                "carb=$carb, " +
                "proteinPercentage=$proteinPercentage, " +
                "carbPercentage=$carbPercentage, " +
                "fatPercentage=$fatPercentage" +
                '}'
    }

    fun getGender(): String {
        return gender
    }

    fun getActivityLevel(): String {
        return activityLevel
    }

    fun getGoal(): String {
        return goal
    }

    fun getAge(): Int {
        return age
    }

    fun getBMR(): Int {
        return BMR
    }

    fun getCalories(): Int {
        return calories
    }

    fun getWeight(): Float {
        return weight
    }

    fun getHeight(): Float {
        return height
    }

    fun getProtein(): Int {
        return protein
    }

    fun getFat(): Int {
        return fat
    }

    fun getCarb(): Int {
        return carb
    }

    fun getProteinPercentage(): Float {
        return proteinPercentage
    }

    fun getCarbPercentage(): Float {
        return carbPercentage
    }

    fun getFatPercentage(): Float {
        return fatPercentage
    }

    fun setGender(gender: String) {
        this.gender = gender
    }

    // Setter for activityLevel
    fun setActivityLevel(activityLevel: String) {
        this.activityLevel = activityLevel
    }

    // Setter for goal
    fun setGoal(goal: String) {
        this.goal = goal
    }

    // Setter for age
    fun setAge(age: Int) {
        this.age = age
    }

    // Setter for BMR
    fun setBMR(BMR: Int) {
        this.BMR = BMR
    }

    // Setter for calories
    fun setCalories(calories: Int) {
        this.calories = calories
    }

    // Setter for weight
    fun setWeight(weight: Float) {
        this.weight = weight
    }

    // Setter for height
    fun setHeight(height: Float) {
        this.height = height
    }

    // Setter for protein
    fun setProtein(protein: Int) {
        this.protein = protein
    }

    // Setter for fat
    fun setFat(fat: Int) {
        this.fat = fat
    }

    // Setter for carb
    fun setCarb(carb: Int) {
        this.carb = carb
    }

    // Setter for proteinPercentage
    fun setProteinPercentage(proteinPercentage: Float) {
        this.proteinPercentage = proteinPercentage
    }

    // Setter for carbPercentage
    fun setCarbPercentage(carbPercentage: Float) {
        this.carbPercentage = carbPercentage
    }

    // Setter for fatPercentage
    fun setFatPercentage(fatPercentage: Float) {
        this.fatPercentage = fatPercentage
    }
}