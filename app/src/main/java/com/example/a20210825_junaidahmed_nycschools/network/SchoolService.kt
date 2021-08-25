package com.example.a20210825_junaidahmed_nycschools.network


import com.example.a20210825_junaidahmed_nycschools.di.DaggerSchoolAPIComponent
import com.example.a20210825_junaidahmed_nycschools.model.School
import com.example.a20210825_junaidahmed_nycschools.model.SchoolPerformance
import io.reactivex.Single
import javax.inject.Inject

class SchoolService {

    @Inject
    lateinit var schoolAPI: SchoolAPI


    init {
    DaggerSchoolAPIComponent.create().inject(this)
    }

    fun getSchools(): Single<List<School>> {
        return schoolAPI.getSchools()
    }


    fun getSchoolDetails(dbn:String?):Single<List<SchoolPerformance>>{
        return schoolAPI.getSchoolDetails(dbn)
    }

}