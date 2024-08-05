package com.example.bfit.di

import com.example.bfit.main.data.repository.AuthStateRepositoryImpl
import com.example.bfit.main.domain.repository.AuthStateRepository
import com.example.bfit.navdrawerfeatures.addFood.data.FoodApi
import com.example.bfit.navdrawerfeatures.addFood.data.remote.FoodAPIResult
import com.example.bfit.util.Constants
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFoodApi(): FoodApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodApi::class.java)
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthStateRepositoryImpl): AuthStateRepository = impl

    @Provides
    @Singleton
    fun providesFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

}