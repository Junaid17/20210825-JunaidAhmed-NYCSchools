package com.example.a20210825_junaidahmed_nycschools.di

import com.example.a20210825_junaidahmed_nycschools.network.SchoolAPI
import com.example.a20210825_junaidahmed_nycschools.network.SchoolService
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient


@Module
class SchoolAPIModule {

    private val BASE_URL="https://data.cityofnewyork.us/resource/"

    @Provides
    fun provideSchoolAPI() :SchoolAPI{

        val interceptor = HttpLoggingInterceptor()
        interceptor.level= HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()


        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(SchoolAPI::class.java)

    }


    @Provides
    fun provideSchoolService():SchoolService{
        return SchoolService()
    }
}