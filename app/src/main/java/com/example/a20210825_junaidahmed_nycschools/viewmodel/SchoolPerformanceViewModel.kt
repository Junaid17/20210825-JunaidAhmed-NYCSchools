package com.example.a20210825_junaidahmed_nycschools.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a20210825_junaidahmed_nycschools.di.DaggerSchoolAPIComponent
import com.example.a20210825_junaidahmed_nycschools.model.SchoolPerformance
import com.example.a20210825_junaidahmed_nycschools.network.SchoolService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchoolPerformanceViewModel : ViewModel() {

    @Inject
    lateinit var schoolService: SchoolService

    private val disposable= CompositeDisposable()

    val schoolsPerformance= MutableLiveData<List<SchoolPerformance>>()
    val school_details_load_error= MutableLiveData<Boolean>()
    val loading_details= MutableLiveData<Boolean>()

    init {
        DaggerSchoolAPIComponent.create().inject(this)
    }


    /*
        * This method is used to fetch details of specific school by passing  dbn as request parameter
        * */
    fun fetchSchoolPerfomrance(dbn:String?){
        loading_details.value=true
        disposable.add(
            schoolService.getSchoolDetails(dbn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<SchoolPerformance>>(){
                    override fun onSuccess(schoolPerformance: List<SchoolPerformance>) {
                        schoolsPerformance.value=schoolPerformance
                        school_details_load_error.value=false
                        loading_details.value=false
                    }
                    override fun onError(e: Throwable) {
                        school_details_load_error.value=true
                        loading_details.value=false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}