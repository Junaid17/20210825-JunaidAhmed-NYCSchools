package com.example.a20210825_junaidahmed_nycschools.network


import com.example.a20210825_junaidahmed_nycschools.model.School
import io.reactivex.Single
import retrofit2.http.GET


interface SchoolAPI {

    // endpoint to retrieve arraylist for loading in the recyclerview in main activity
    @GET("97mf-9njv.json?\$select=borough,dbn,school_name,location")
    fun getSchools(): Single<List<School>>
}