package com.example.bfit.navdrawerfeatures.adjust_calories.data.repository

import android.util.Log
import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.repository.MacrosRepository
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MacrosRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : MacrosRepository {
    override fun getProfileRealtime(userUid: String): Flow<Resource<UserInfo>> = callbackFlow {
        Log.d("MacrosRepositoryImpl", "Am intrat")
        // Creating a reference to the cars collection
        val docRef = firebaseFirestore.collection("users").document(userUid)

        // Listen to data real-time
        val listener = docRef.addSnapshotListener { documentSnapshot, e ->
            if (e != null) {
                /* TODO: Handle the error */
                Log.d("MacrosRepositoryImpl", "Error")
                trySend(Resource.Error(message = e.message ?: "Unknown error")).isSuccess
                return@addSnapshotListener
            }

            if (documentSnapshot != null) {
                // Converts the result data to our List<Car>
                val userInfo = documentSnapshot.toObject(UserInfo::class.java)
                Log.d("MacrosRepositoryImpl", "Succes")
                // Emits the data
                userInfo?.let {
                    trySend(Resource.Success<UserInfo>(data = userInfo)).isSuccess
                }
            }
        }

        awaitClose {
            // Remove the database listener
            listener.remove()
            close()
        }
    }

    override suspend fun setProfileDocument(
        userUid: String,
        gender: String,
        activityLevel: String,
        goal: String,
        age: Int,
        weight: Float,
        height: Float,
        protein: Int,
        carb: Int,
        fat: Int,
        proteinPercentage: Float,
        carbPercentage: Float,
        fatPercentage: Float,
        calories: Int
    ): Response<Boolean> = try {
        val user = UserInfo(
            gender,
            activityLevel,
            goal,
            age,
            weight,
            height,
            protein,
            carb,
            fat,
            proteinPercentage,
            carbPercentage,
            fatPercentage,
            calories
        )
        val docRef = firebaseFirestore.collection("users").document(userUid)
        docRef.set(user).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }


    override suspend fun updateProfileDocument(
        userUid: String,
        calories: Int,
        protein: Int,
        carb: Int,
        fat: Int,
        proteinPercentage: Float,
        carbPercentage: Float,
        fatPercentage: Float
    ): Response<Boolean> = try {
        val docRef = firebaseFirestore.collection("users").document(userUid)
        docRef
            .update(
                "calories", calories,
                "protein", protein,
                "carb", carb,
                "fat", fat,
                "proteinPercentage", proteinPercentage,
                "carbPercentage", carbPercentage,
                "fatPercentage", fatPercentage
            )
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}