package com.example.bfit.navdrawerfeatures.addFood.domain.use_case

import com.example.bfit.navdrawerfeatures.addFood.data.remote.FoodAPIResult
import com.example.bfit.navdrawerfeatures.addFood.domain.repository.AddFoodRepository
import com.example.bfit.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetFoodUseCase @Inject constructor(
    private val addFoodRepository: AddFoodRepository
) {
   operator fun invoke (food:String): Flow<Resource<FoodAPIResult>>  = flow{
       try{
           emit(Resource.Loading())
           val foodResult = addFoodRepository.getFood(food)
           emit(Resource.Success(foodResult))
       } catch(e:HttpException){
           emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
       } catch (e:IOException){
           emit(Resource.Error("Couldn't reach server. Check your internet connection."))
       }

   }
}