package com.example.bfit.navdrawerfeatures.showMealsFood.data.repository

import android.util.Log
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.repository.ShowMealsFoodRepository
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
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

    override suspend fun updateMacros(
        uid: String,
        formattedDate: String,
        food: FoodInfoModel
    ): Response<Boolean> =try{
        val daysCollection = firebaseFirestore.collection("users")
            .document(uid)
            .collection("Day Tracker")
        val daysDocument = daysCollection.document(formattedDate)
        val documentSnapshot = suspendCancellableCoroutine { continuation ->
            daysDocument.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(task.result)
                } else {
                    continuation.resumeWithException(
                        task.exception ?: Exception("Unknown error")
                    )
                }
            }
        }
        if(documentSnapshot.exists()) {
            val kcalValue = documentSnapshot.getDouble("total_kcal")
            val proteinValue = documentSnapshot.getDouble("total_protein")
            val carbValue = documentSnapshot.getDouble("total_carb")
            val fatValue = documentSnapshot.getDouble("total_fat")

            val previousTotalKcal = kcalValue?.let {
               round(it)
            } ?: 0.0
            val previousTotalProtein = proteinValue?.let {
                round(it)
            } ?: 0.0
            val previousTotalCarb = carbValue?.let { round(it)
            } ?: 0.0
            val previousTotalFat = fatValue?.let {
                round(it)
            } ?: 0.0

            Log.d("ShowMealsFoodImpl", "previousTotalKcal =$previousTotalKcal , enercKcal=${food.enercKcal.toDouble()} ")
            daysDocument
                .update(
                    "total_kcal",previousTotalKcal - food.enercKcal.toDouble(),
                    "total_protein",previousTotalProtein - food.prot.toDouble(),
                    "total_carb",previousTotalCarb - food.carb.toDouble(),
                    "total_fat", previousTotalFat - food.fat.toDouble()

                    )
            Response.Success(true)
        }
      Response.Success(true)
    }catch (e:Exception){
        Response.Failure(e)
    }

    override suspend fun deleteFoodFromDatabase(
        uid: String,
        formattedDate: String,
        food: FoodInfoModel,
        meal: String
    ): Response<Boolean> = try {
        val daysCollection = firebaseFirestore.collection("users")
            .document(uid)
            .collection("Day Tracker")
        val daysDocument = daysCollection.document(formattedDate)
        daysDocument
            .collection(meal).document(food.label).delete().await()
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
    private fun Double.roundToString(): String = round(this).toString()
//    private fun Double.round(decimals: Int = 1): Double {
//        val scale = 10.0.pow(decimals)
//        return (round(this * scale) / scale)
//    }
}
