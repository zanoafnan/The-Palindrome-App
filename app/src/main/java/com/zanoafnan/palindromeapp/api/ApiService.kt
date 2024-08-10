package com.zanoafnan.palindromeapp.api

import com.zanoafnan.palindromeapp.response.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/users")
    suspend fun getUsers(@Query("page") page: Int, @Query("per_page") perPage: Int): Response<UsersResponse>
}
