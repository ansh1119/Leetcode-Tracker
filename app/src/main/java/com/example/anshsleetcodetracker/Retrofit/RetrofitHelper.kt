package com.example.anshsleetcodetracker.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private const val BASE_URL = "https://a295-43-230-37-207.ngrok-free.app/"
        val gson = GsonBuilder()
            .setLenient()
            .create()
        // Function to provide Retrofit instance
        fun providesPublicRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)) // Add Gson converter
                .build()
        }
    }
}
