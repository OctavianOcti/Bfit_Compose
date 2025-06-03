package com.example.bfit.navdrawerfeatures.customMealFood.food.viewFood.data

import android.util.Log
import com.example.bfit.navdrawerfeatures.customMealFood.food.viewFood.domain.CustomFoodRepository
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CustomFoodRepositoryImpl @Inject constructor (
    private val firebaseFirestore: FirebaseFirestore
): CustomFoodRepository {
    override fun getFood(
        uid: String,
    ): Flow<Resource<List<FoodInfoModel>>> = flow {
        try {
            emit(Resource.Loading())
            val foodCollection = firebaseFirestore.collection("users")
                .document(uid)
                .collection("Food")

            val querySnapshot = foodCollection.get().await()
            val foodInfoModelList = querySnapshot.documents.map { document ->
                document.toFoodInfoModel()
            }
            emit(Resource.Success(foodInfoModelList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

//    override suspend fun updateMacros(uid: String): Response<Boolean> {
//    return Response<True>
//    }

    override suspend fun deleteFoodFromDatabase(
        uid: String,
        food: FoodInfoModel,
    ): Response<Boolean> = try {
        val foodCollection = firebaseFirestore.collection("users")
            .document(uid)
            .collection("Food")
        val foodDocument = foodCollection.document(food.label)
        foodDocument
            .delete().await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    private fun DocumentSnapshot.toFoodInfoModel(): FoodInfoModel{
        val label = id
        val brand = getString("brand") ?: ""
        val servingType = getString("servingType") ?: ""
//        val kcal = getDouble("kcal")?.roundToString() ?: ""
//        val carb = getDouble("carb")?.roundToString() ?: ""
//        val fat = getDouble("fat")?.roundToString() ?: ""
//        val protein = getDouble("protein")?.roundToString() ?: ""
//        val servingSize = getDouble("serving_size")?.roundToString() ?: ""

        val kcal = getDouble("kcal").toString() ?: ""
        val carb = getDouble("carb").toString() ?: ""
        val fat = getDouble("fat").toString() ?: ""
        val protein = getDouble("protein").toString() ?: ""
        val servingSize = getDouble("serving_size").toString() ?: ""

        Log.d("FoodInfoModel", FoodInfoModel(
            label, brand, kcal, protein, fat, carb, "", servingSize, servingType
        ).toString()
        )
        return FoodInfoModel(
            label, brand, kcal, protein, fat, carb, "", servingSize, servingType
        )
    }
}