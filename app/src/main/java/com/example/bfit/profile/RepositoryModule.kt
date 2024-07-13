package com.example.bfit.profile

import com.example.bfit.profile.data.ProfileRepositoryImpl
import com.example.bfit.profile.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class  RepositoryModule {

    @Binds
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

}