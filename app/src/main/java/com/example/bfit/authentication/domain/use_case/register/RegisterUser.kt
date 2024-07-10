package com.example.bfit.authentication.domain.use_case.register

import com.example.bfit.authentication.domain.repository.AuthRepository
import com.example.bfit.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val repository: AuthRepository
) {
//    operator fun invoke(email: String, password: String): Flow<Resource<Unit>> {
//        return flow {
//            repository.registerUser(email, password).collectLatest { result ->
//                when (result) {
//                    is Resource.Loading -> emit(Resource.Loading())
//                    is Resource.Success -> emit(Resource.Success(Unit))
//                    is Resource.Error -> emit(
//                        Resource.Error(
//                            result.message ?: "An unknown error occurred"
//                        )
//                    )
//                }
//            }
//        }
//    }

    operator fun invoke (email: String, password: String): Flow<Resource<AuthResult>>{
        return repository.registerUser(email,password)
    }
}
