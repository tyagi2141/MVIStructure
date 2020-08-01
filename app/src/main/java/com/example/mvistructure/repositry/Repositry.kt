package com.example.mvistructure.repositry

import androidx.lifecycle.LiveData
import com.example.mvistructure.api.AppRetroFitBuilder
import com.example.mvistructure.ui.main.model.BlogPost
import com.example.mvistructure.ui.main.model.User
import com.example.mvistructure.ui.main.state.MainViewState
import com.example.mvistructure.util.ApiSuccessResponse
import com.example.mvistructure.util.DataState
import com.example.mvistructure.util.GenericApiResponse

/**
 * Created by Rahul on 01/08/20.
 */
object Repositry {

    fun getBlogPost(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {
            override fun handelApiResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    message = null,
                    data = MainViewState(
                        blogPost = response.body,
                        user = null
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return AppRetroFitBuilder.apiService.getBlogPosts()
            }

        }.asLiveData()
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {
            override fun handelApiResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    null,
                    data = MainViewState(
                        user = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return AppRetroFitBuilder.apiService.getUser(userId)
            }

        }.asLiveData()
    }
}