package com.example.bfit.navdrawerfeatures.profile.data.repository

import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.navdrawerfeatures.profile.domain.repository.ProfileRepository
import com.example.bfit.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : ProfileRepository {

    override fun getProfileOnce(): Flow<Resource<List<UserInfo>>> = callbackFlow {
        // Creating a reference to the cars collection
        val docRef = firebaseFirestore.collection("cars")

        // Get the data
        docRef.get()
            .addOnSuccessListener { result ->
                // Converts the result data to our List<Car>
                val cars = result.toObjects<UserInfo>()

                // Emits the data
                trySend(Resource.Success<List<UserInfo>>(data = cars)).isSuccess
            }
            .addOnFailureListener { exception ->
                /* TODO: Handle the error */
            }

        awaitClose {
            close()
        }
    }

    override fun getProfileRealtime(userUid : String ): Flow<Resource<UserInfo>> = callbackFlow {
        // Creating a reference to the cars collection
        val docRef = firebaseFirestore.collection("users").document(userUid)

        // Listen to data real-time
        val listener =  docRef.addSnapshotListener { documentSnapshot, e ->
            if (e != null) {
                /* TODO: Handle the error */
                return@addSnapshotListener
            }

            if (documentSnapshot != null) {
                // Converts the result data to our List<Car>
                val userInfo = documentSnapshot.toObject(UserInfo::class.java)

                // Emits the data
               userInfo?.let{
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
}