package com.example.brocoli.data.remote

import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private val moshi = Moshi.Builder()
    .build()
private val BASE_URL = "https://us-central1-blinkapp-684c1.cloudfunctions.net"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface BroccoliApi {
    @POST("/fakeAuth")
    fun register(@Body input: RegistrationBody): Call<Void>
}

object BroccoliApiService {
    val myRetrofit : BroccoliApi by lazy {
        retrofit.create(BroccoliApi::class.java) }
}