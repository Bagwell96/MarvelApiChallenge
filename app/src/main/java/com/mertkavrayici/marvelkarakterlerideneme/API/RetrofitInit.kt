package com.mertkavrayici.marvelkarakterlerideneme.API

import com.mertkavrayici.marvelkarakterlerideneme.Utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInit {

private fun initRetrofit():Retrofit{

return retrofit2.Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
}
    val service: RetrofitService= initRetrofit().create(RetrofitService::class.java)

}