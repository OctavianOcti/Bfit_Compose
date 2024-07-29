package com.example.bfit.navdrawerfeatures.showMealsFood.data.repository

import android.util.Log
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.repository.ShowMealsFoodRepository
import com.example.bfit.util.Resource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.round

class ShowMealsFoodImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : ShowMealsFoodRepository {

    override fun getFood(
        uid: String,
        formattedDate: String,
        meal: String
    ): Flow<Resource<List<FoodInfoModel>>> = flow {
        try {
            emit(Resource.Loading())
            val mealCollection = firebaseFirestore.collection("users")
                .document(uid)
                .collection("Day Tracker").document(formattedDate).collection(meal)

            val querySnapshot = mealCollection.get().await()
            val foodInfoModelList = querySnapshot.documents.map { document ->
                document.toFoodInfoModel()
            }
            emit(Resource.Success(foodInfoModelList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
    private fun DocumentSnapshot.toFoodInfoModel(): FoodInfoModel{
        val label = id
        val brand = getString("brand") ?: ""
        val servingType = getString("servingType") ?: ""
        val kcal = getDouble("kcal")?.roundToString() ?: ""
        val carb = getDouble("carb")?.roundToString() ?: ""
        val fat = getDouble("fat")?.roundToString() ?: ""
        val protein = getDouble("protein")?.roundToString() ?: ""
        val servingSize = getDouble("serving_size")?.roundToString() ?: ""
        Log.d("FoodInfoModel", FoodInfoModel(
                label, brand, kcal, protein, fat, carb, "", servingSize, servingType
            ).toString()
        )
        return FoodInfoModel(
            label, brand, kcal, protein, fat, carb, "", servingSize, servingType
        )
    }
    private fun Double.roundToString(): String = this.round(1).toString()
    private fun Double.round(decimals: Int = 1): Double {
        val scale = 10.0.pow(decimals)
        return (round(this * scale) / scale)
    }
}
