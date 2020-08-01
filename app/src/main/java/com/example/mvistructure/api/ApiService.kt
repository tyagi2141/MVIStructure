package com.example.mvistructure.api

import androidx.lifecycle.LiveData
import com.example.mvistructure.ui.main.model.BlogPost
import com.example.mvistructure.ui.main.model.User
import com.example.mvistructure.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Rahul on 31/07/20.
 */
interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun getBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>

}