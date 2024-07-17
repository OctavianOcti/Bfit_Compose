package com.example.bfit.di

import com.example.bfit.main.data.repository.AuthStateRepositoryImpl
import com.example.bfit.main.domain.repository.AuthStateRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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