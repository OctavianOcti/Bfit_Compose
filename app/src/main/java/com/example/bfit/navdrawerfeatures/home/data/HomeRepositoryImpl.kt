package com.example.bfit.navdrawerfeatures.home.data
import android.util.Log
import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.navdrawerfeatures.home.domain.DailyInfo
import com.example.bfit.navdrawerfeatures.home.domain.HomeRepository
import com.example.bfit.navdrawerfeatures.home.presentation.model.DailyKcalEntry
import com.example.bfit.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : HomeRepository {
    override fun getProfileRealtime(userUid: String): Flow<Resource<UserInfo>> = callbackFlow {
        Log.d("HomeRepositoryImpl", "Am intrat")
        // Creating a reference to the cars collection
        val docRef = firebaseFirestore.collection("users").document(userUid)

        // Listen to data real-time
        val listener = docRef.addSnapshotListener { documentSnapshot, e ->
            if (e != null) {
                /* TODO: Handle the error */
                Log.d("HomeRepositoryImpl", "Error")
                trySend(Resource.Error(message = e.message ?: "Unknown error")).isSuccess
                return@addSnapshotListener
            }

            if (documentSnapshot != null) {
                // Converts the result data to our List<Car>
                val userInfo = documentSnapshot.toObject(UserInfo::class.java)
                Log.d("HomeRepositoryImpl", "Succes")
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

    override fun getTotalDailyInfo(
        userUid: String,
        formattedDate: String
    ): Flow<Resource<DailyInfo>> = callbackFlow {
        Log.d("HomeRepositoryImpl getTotalDailyInfo", "Am intrat")
        val docRef = firebaseFirestore
                .collection("users")
                .document(userUid)
                .collection("Day Tracker")
                .document(formattedDate)
        Log.d("FirestorePath", "Document path: ${docRef.path}")
        // Listen to data real-time
        val listener = docRef.addSnapshotListener { documentSnapshot, e ->
            if (e != null) {
                /* TODO: Handle the error */
                Log.d("HomeRepositoryImpl getTotalDailyInfo", "Error")
                trySend(Resource.Error(message = e.message ?: "Unknown error")).isSuccess
                return@addSnapshotListener
            }

            if (documentSnapshot != null) {
                // Converts the result data to our List<Car>
                val dailyInfo = documentSnapshot.toObject(DailyInfo::class.java)
                Log.d("HomeRepositoryImpl getTotalDailyInfo ", "Succes")
                // Emits the data
                dailyInfo?.let {
                    trySend(Resource.Success(data = dailyInfo)).isSuccess
                    Log.d("HomeRepositoryImpl getTotalDailyInfo",dailyInfo.toString())
                } ?:run {
                    Log.d("HomeRepositoryImpl getTotalDailyInfo", "dailyInfo is null")
                    trySend(Resource.Error("DailyInfo is null"))

                }
            }
        }
        awaitClose {

            // Remove the database listener
            listener.remove()
            close()
        }
    }

    override suspend fun getLast7DaysKcal(
        userUid: String,
        today: String
    ): Flow<Resource<List<DailyKcalEntry>>> = flow {
        emit(Resource.Loading())

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val todayDate = dateFormat.parse(today)

        val snapshot = firebaseFirestore
            .collection("users")
            .document(userUid)
            .collection("Day Tracker")
            .get()
            .await()

        val filtered = snapshot.documents
            .filter { document ->
                val documentDate = try {
                    dateFormat.parse(document.id)
                } catch (e: ParseException) {
                    null
                }
                documentDate != null && documentDate.before(todayDate)
            }
            .sortedByDescending { dateFormat.parse(it.id) }

        val last7 = filtered.take(7)

        val result = last7.mapNotNull { doc ->
            val idParts = doc.id.split("-")
            if (idParts.size >= 2) {
                val label = "${idParts[0]}-${idParts[1]}"
                val kcal = doc.get("total_kcal")?.toString() ?: return@mapNotNull null
                DailyKcalEntry(label, kcal)
            } else null
        }

        emit(Resource.Success(data = result))

    }.catch { e ->
        emit(Resource.Error(message = e.message ?: "Unknown error"))
    }

}