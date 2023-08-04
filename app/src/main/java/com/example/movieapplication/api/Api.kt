package com.example.movieapplication.api

import androidx.room.PrimaryKey
import com.example.movieapplication.data.GetMoviesResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "4877fced511b935021ca3e77da2a4413",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>


    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "4877fced511b935021ca3e77da2a4413",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>


    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = "4877fced511b935021ca3e77da2a4413",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>


    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "4877fced511b935021ca3e77da2a4413",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>



}

