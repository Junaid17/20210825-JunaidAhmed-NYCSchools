package com.example.a20210825_junaidahmed_nycschools.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a20210825_junaidahmed_nycschools.model.School

class SchoolViewModel : ViewModel() {

    val schoolname=MutableLiveData<List<School>>()
    val school_load_error=MutableLiveData<Boolean>()
    val loading=MutableLiveData<Boolean>()

    fun refreshData(){
        fetchSchool()
    }

    private fun fetchSchool(){

        val mockData= listOf(School("asd","Pillay"),
        School("123","NKT"),
        School("123","sdhjhsj"),
        School("1","sfh"),
        School("2","fsahf"),
        School("3","afdshgfa"))

        school_load_error.value=false
        loading.value=false
        schoolname.value=mockData
    }

}