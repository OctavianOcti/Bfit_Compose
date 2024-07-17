package com.example.bfit.authentication.di

import com.example.bfit.authentication.domain.use_case.data.AndroidEmailPatternValidator
import com.example.bfit.authentication.domain.use_case.data.AndroidPasswordPatternValidator
import com.example.bfit.authentication.data.repository.AuthRepositoryImpl
import com.example.bfit.authentication.domain.use_case.AuthUseCases
import com.example.bfit.authentication.domain.use_case.register.RegisterUser
import com.example.bfit.authentication.domain.use_case.ValidateEmail
import com.example.bfit.authentication.domain.use_case.ValidatePassword
import com.example.bfit.authentication.domain.use_case.register.ValidateRepeatedPassword
import com.example.bfit.authentication.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    fun providesAuthRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth = firebaseAuth)
    }

    @Provides
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            validateEmail = ValidateEmail(AndroidEmailPatternValidator()),
            validatePassword = ValidatePassword(AndroidPasswordPatternValidator()),
            validateRepeatedPassword = ValidateRepeatedPassword(),
            registerUser = RegisterUser(repository = repository)

        )
    }

}