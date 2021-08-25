package com.example.a20210825_junaidahmed_nycschools.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a20210825_junaidahmed_nycschools.di.DaggerSchoolAPIComponent
import com.example.a20210825_junaidahmed_nycschools.model.School
import com.example.a20210825_junaidahmed_nycschools.network.SchoolService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchoolViewModel : ViewModel() {

    @Inject
    lateinit var schoolService: SchoolService

    private val disposable= CompositeDisposable()
    val schools=MutableLiveData<List<School>>()
    val school_load_error=MutableLiveData<Boolean>()
    val loading=MutableLiveData<Boolean>()

    init {
        DaggerSchoolAPIComponent.create().inject(this)
    }

    fun refreshData()= fetchSchool()


    private fun fetchSchool(){

        loading.value=true
        disposable.add(
            schoolService.getSchools()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<School>>(){
                    override fun onSuccess(schoolList: List<School>) {
                        schools.value=schoolList
                        school_load_error.value=false
                        loading.value=false
                    }

                    override fun onError(e: Throwable) {
                        school_load_error.value=true
                        loading.value=false
                    }

                })
        )


    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}