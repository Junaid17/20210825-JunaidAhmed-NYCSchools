package com.example.a20210825_junaidahmed_nycschools.network


import com.example.a20210825_junaidahmed_nycschools.model.School
import com.example.a20210825_junaidahmed_nycschools.model.SchoolPerformance
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface SchoolAPI {

    // endpoint to retrieve arraylist for loading in the recyclerview in main activity
    @GET("97mf-9njv.json?\$select=borough,dbn,school_name,location")
    fun getSchools(): Single<List<School>>


    @GET("734v-jeq5.json?")
    fun getSchoolDetails(@Query("dbn") dbn: String?): Single<List<SchoolPerformance>>

}