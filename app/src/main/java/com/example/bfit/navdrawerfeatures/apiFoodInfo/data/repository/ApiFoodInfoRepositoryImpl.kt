package com.example.bfit.navdrawerfeatures.apiFoodInfo.data.repository

import android.util.Log
import com.example.bfit.navdrawerfeatures.apiFoodInfo.domain.repository.ApiFoodInfoRepository
import com.example.bfit.navdrawerfeatures.common.presentation.domain.round
import com.example.bfit.navdrawerfeatures.foodInfo.domain.PreviousMacros
import com.example.bfit.navdrawerfeatures.quickAdd.domain.PreviousFoodMacros
import com.example.bfit.navdrawerfeatures.quickAdd.domain.repository.QuickAddRepository
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ApiFoodInfoRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : ApiFoodInfoRepository {
    override fun getPreviousTotalMacros(
        uid: String,
        formattedDate: String
    ): Flow<Resource<PreviousMacros>> = flow {
        emit(Resource.Loading())
        try {
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
            Log.d("ApiFoodInfoRepositoryImpl", formattedDate)
            Log.d("ApiFoodInfoRepositoryImpl", documentSnapshot.exists().toString())
            if (documentSnapshot.exists()) {
                val kcalValue = documentSnapshot.getDouble("total_kcal")
                val proteinValue = documentSnapshot.getDouble("total_protein")
                val carbValue = documentSnapshot.getDouble("total_carb")
                val fatValue = documentSnapshot.getDouble("total_fat")

                val previousTotalKcal = kcalValue?.let { round(it) } ?: 0.0
                val previousTotalProtein = proteinValue?.let { round(it) } ?: 0.0
                val previousTotalCarb = carbValue?.let { round(it) } ?: 0.0
                val previousTotalFat = fatValue?.let { round(it) } ?: 0.0


                Log.d(
                    "ApiFoodInfoRepositoryImpl",
                    "previousTotalKcal = $previousTotalKcal , previoustotalProtein = $previousTotalProtein" +
                            "previousTotalCarb = $previousTotalCarb, previousTotalFat= $previousTotalFat"
                )
                emit(
                    Resource.Success(
                        PreviousMacros(
                            previousTotalKcal,
                            previousTotalProtein,
                            previousTotalCarb,
                            previousTotalFat
                        )
                    )
                )

            } else {
                emit(Resource.Error(message = "Document does not exist"))
            }

        } catch (e: Exception) {
            emit(Resource.Error(message = e.toString()))
        }
    }

    override fun getPreviousFoodMacros(
        uid: String,
        formattedDate: String,
        meal: String,
        foodLabel: String
    ): Flow<Resource<PreviousFoodMacros>> = flow {
        emit(Resource.Loading())
        try {
            val daysCollection = firebaseFirestore.collection("users")
                .document(uid)
                .collection("Day Tracker")
            val daysDocument = daysCollection.document(formattedDate)
            val foodDocument = daysDocument.collection(meal).document(foodLabel)
            val documentSnapshot = suspendCancellableCoroutine { continuation ->
                foodDocument.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(task.result)
                    } else {
                        continuation.resumeWithException(
                            task.exception ?: Exception("Unknown error")
                        )
                    }
                }
            }
            if (documentSnapshot.exists()) {
                val foodKcalValue = documentSnapshot.getDouble("kcal")
                val foodProteinValue = documentSnapshot.getDouble("protein")
                val foodCarbValue = documentSnapshot.getDouble("carb")
                val foodFatValue = documentSnapshot.getDouble("fat")

                val previousFoodKcal = foodKcalValue?.let { round(it) } ?: 0.0
                val previousFoodProtein = foodProteinValue?.let { round(it) } ?: 0.0
                val previousFoodCarb = foodCarbValue?.let { round(it) } ?: 0.0
                val previousFoodFat = foodFatValue?.let { round(it) } ?: 0.0

                Log.d(
                    "ApiFoodInfoRepositoryImpl",
                    "previousFoodKcal = $previousFoodKcal , previousFoodProtein = $previousFoodProtein" +
                            "previousFoodCarb = $previousFoodCarb, previousFoodFat= $previousFoodFat"
                )
                emit(
                    Resource.Success(
                        PreviousFoodMacros(
                            previousFoodKcal,
                            previousFoodProtein,
                            previousFoodCarb,
                            previousFoodFat
                        )
                    )
                )
            } else {
                emit(Resource.Error(message = "Food Document does not exist"))
            }

        } catch (e: Exception) {
            emit(Resource.Error(message = e.toString()))
        }
    }

    override suspend fun updateFoodDocument(
        uid: String,
        formattedDate: String,
        dayInfoMap: Map<String, Any>,
        foodMap: Map<String, Any>,
        kcal: String,
        protein: String,
        carb: String,
        fat: String,
        previousTotalKcal: String,
        previousTotalProtein: String,
        previousTotalCarb: String,
        previousTotalFat: String,
        meal: String,
        foodLabel: String
    ): Response<Boolean> = try {
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
        if (documentSnapshot.exists()) {
            daysDocument
                .update(
                    "total_kcal", round(previousTotalKcal.toDouble() + kcal.toDouble()),
                    "total_protein", round(previousTotalProtein.toDouble() + protein.toDouble()),
                    "total_carb", round(previousTotalCarb.toDouble() + carb.toDouble()),
                    "total_fat", round(previousTotalFat.toDouble() + fat.toDouble())
                )

            daysDocument
                .collection(meal)
                .document(foodLabel)
                .set(foodMap, SetOptions.merge())
            Response.Success(true)

        } else {
            daysDocument.set(dayInfoMap, SetOptions.merge())
            daysDocument
                .collection(meal)
                .document(foodLabel)
                .set(foodMap, SetOptions.merge())
            Response.Success(true)

        }
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun setNewDocument(
        uid: String,
        formattedDate: String,
        dayInfoMap: Map<String, Any>,
        foodMap: Map<String, Any>,
        meal: String,
        foodLabel: String
    ): Response<Boolean> = try {

        val daysCollection = firebaseFirestore.collection("users")
            .document(uid)
            .collection("Day Tracker")
        val daysDocument = daysCollection.document(formattedDate)
        daysDocument.set(dayInfoMap, SetOptions.merge())
        daysDocument
            .collection(meal)
            .document(foodLabel)
            .set(foodMap, SetOptions.merge())
        Response.Success(true)

    } catch (e: Exception) {
        Response.Failure(e)
    }
}