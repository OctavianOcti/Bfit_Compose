package com.example.bfit.navdrawerfeatures.goals.data.repository

import android.util.Log
import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.navdrawerfeatures.goals.domain.repository.GoalsRepository
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoalsRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : GoalsRepository {

    override fun getProfileRealtime(userUid: String): Flow<Resource<UserInfo>> = callbackFlow {
        // Creating a reference to the cars collection
        val docRef = firebaseFirestore.collection("users").document(userUid)

        // Listen to data real-time
        val listener = docRef.addSnapshotListener { documentSnapshot, e ->
            if (e != null) {
                /* TODO: Handle the error */
                trySend(Resource.Error(message = e.message ?: "Unknown error")).isSuccess
                return@addSnapshotListener
            }

            if (documentSnapshot != null) {
                // Converts the result data to our List<Car>
                val userInfo = documentSnapshot.toObject(UserInfo::class.java)

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

    override fun setProfileDocument(
        userUid: String,
        gender: String,
        weight: Float,
        height: Float,
        age: Int,
        activityLevel: String,
        goal: String
    ): Flow<Resource<Any>> = callbackFlow {
        // Emit loading state
        //trySend(Resource.Loading())
        Log.d("GoalsRepositoryImpl","Am intrat")

        val user = UserInfo(gender, activityLevel, goal, age, weight, height)
        val docRef = firebaseFirestore.collection("users").document(userUid)

        docRef.set(user)
            .addOnSuccessListener {
                // Emit success state
                Log.d("GoalsRepositoryImpl", "succes")
                trySend(Resource.Success(data = "Profile updated successfully")).isSuccess
                close() // Close the flow
            }
            .addOnFailureListener { e ->
                // Emit error state
                Log.d("GoalsRepositoryImpl", "error")
                trySend(Resource.Error(message = e.message ?: "Failed to update profile")).isSuccess
                close() // Close the flow
            }

        awaitClose {
            // Close the flow if canceled
            close()
        }
    }

    override suspend fun setProfileDocument1(
        userUid: String,
        gender: String,
        weight: Float,
        height: Float,
        age: Int,
        activityLevel: String,
        goal: String
    ): Response<Boolean> = try {
        val user = UserInfo(gender, activityLevel, goal, age, weight, height)
        val docRef = firebaseFirestore.collection("users").document(userUid)
        docRef.set(user).await()
        Response.Success(true)
    }catch (e: Exception){
        Response.Failure(e)
    }



}