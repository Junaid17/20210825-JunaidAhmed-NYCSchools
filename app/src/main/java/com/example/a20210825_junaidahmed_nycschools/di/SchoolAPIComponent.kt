package com.example.a20210825_junaidahmed_nycschools.di

import com.example.a20210825_junaidahmed_nycschools.network.SchoolService
import com.example.a20210825_junaidahmed_nycschools.viewmodel.SchoolViewModel
import dagger.Component

@Component(modules = [SchoolAPIModule::class])
interface SchoolAPIComponent {

    fun inject(schoolService: SchoolService)
    fun inject(schoolViewModel: SchoolViewModel)
}