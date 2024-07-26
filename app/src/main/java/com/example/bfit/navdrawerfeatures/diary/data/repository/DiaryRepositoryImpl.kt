package com.example.bfit.navdrawerfeatures.diary.data.repository

import com.example.bfit.navdrawerfeatures.diary.domain.repository.DiaryRepository
import com.example.bfit.util.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class DiaryRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : DiaryRepository {

    override fun getMealTexts(uid: String, formattedDate: String): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading())

        val dayDocument = firebaseFirestore.collection("users")
            .document(uid)
            .collection("Day Tracker").document(formattedDate)
        val meals = arrayOf("Breakfast", "Lunch", "Dinner", "Snacks")
        val mealTexts = MutableList(meals.size) { "" }

        try {
            meals.forEachIndexed { index, meal ->
                val mealCollection = dayDocument.collection(meal)
                val mealText = getMealTextFromCollection(mealCollection)
                mealTexts[index] = mealText
            }

            emit(Resource.Success(mealTexts))
        } catch (e: Exception) {
            emit(Resource.Error(
                message = e.message.toString(),
                data = mealTexts
            ))
        }
    }

    private suspend fun getMealTextFromCollection(mealCollection: CollectionReference): String =
        suspendCancellableCoroutine { continuation ->
            mealCollection.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = if (task.result.isEmpty) {
                        "No tracked food available"
                    } else {
                        "Tap to view your tracked food"
                    }
                    continuation.resume(result)
                } else {
                    val exception = task.exception
                    if (exception != null) {
                        continuation.resumeWithException(exception)
                    } else {
                        continuation.resume("Error retrieving data")
                    }
                }
            }
        }
}
