package com.example.bfit.navdrawerfeatures.customMealFood.createFood.data

import com.example.bfit.navdrawerfeatures.customMealFood.createFood.domain.repository.CreateFoodRepository
import com.example.bfit.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

class CreateFoodRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : CreateFoodRepository  {
    override suspend fun setNewDocument(
        uid: String,
        foodMap: Map<String, Any>,
        foodLabel: String
    ): Response<Boolean> = try{

//        val daysCollection = firebaseFirestore.collection("users")
//            .document(uid)
//            .collection("Food")
//        val foodDocument = daysCollection.document(formattedDate)
//        foodDocument.set(foodMap, SetOptions.merge())
//        foodDocument
//            .collection(meal)
//            .document(foodLabel)
//            .set(foodMap, SetOptions.merge())
        Response.Success(true)

    } catch (e: Exception) {
        Response.Failure(e)
    }


}