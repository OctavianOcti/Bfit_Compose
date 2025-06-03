package com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.data

import android.util.Log
import com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.domain.CustomMealRepository
import com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.domain.MealInfoModel
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CustomMealRepositoryImpl @Inject constructor (
    private val firebaseFirestore: FirebaseFirestore
): CustomMealRepository {
    override fun getMeals(
        uid: String,
    ): Flow<Resource<List<MealInfoModel>>> = flow {
        try {
            emit(Resource.Loading())
            val mealCollection = firebaseFirestore.collection("users")
                .document(uid)
                .collection("Meals")

            val querySnapshot = mealCollection.get().await()
            val mealInfoModelList = querySnapshot.documents.map { document ->
                document.toMealInfoModel()
            }
            emit(Resource.Success(mealInfoModelList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override suspend fun deleteMealFromDatabase(
        uid: String,
        meal: MealInfoModel
    ): Response<Boolean> = try{
        val mealsCollection = firebaseFirestore.collection("users")
            .document(uid)
            .collection("Meals")
        val mealDocument = mealsCollection.document(meal.label)
        val ingredientsCollection = mealDocument.collection("Ingredients")
        val ingredientsSnapshot = ingredientsCollection.get().await()

        for (document in ingredientsSnapshot.documents) {
            document.reference.delete().await()
        }
        mealDocument.delete().await()

        Response.Success(true)
    }catch (e: Exception) {
        Response.Failure(e)
    }

    private fun DocumentSnapshot.toMealInfoModel(): MealInfoModel {
        val label = id
        val kcal = getDouble("total_kcal").toString()
        val carb = getDouble("total_carb").toString()
        val fat = getDouble("total_fat").toString()
        val protein = getDouble("total_protein").toString()
        val servingType = getString("servingType").toString()

        Log.d("MealInfoModel", MealInfoModel(
            label,kcal, protein, fat, carb, servingType
        ).toString()
        )
        return MealInfoModel(
                label,kcal, protein, fat, carb, servingType
        )
    }

}