package com.example.a20210825_junaidahmed_nycschools.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.a20210825_junaidahmed_nycschools.model.SchoolPerformance
import com.example.a20210825_junaidahmed_nycschools.network.SchoolService
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class SchoolPerformanceViewModelTest {


    @get:Rule
    val rule= InstantTaskExecutorRule()

    @Mock
    lateinit var schoolService: SchoolService

    @InjectMocks
    var schoolPerformanceViewModel=SchoolPerformanceViewModel()

    private var singleTest: Single<List<SchoolPerformance>>?=null


    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
    }

    @Before
    fun setUpRxJava() {

        val immediate= object : Scheduler(){

            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { schedular -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { schedular -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { schedular -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { schedular -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { schedular -> immediate }
    }


    @Test
    fun getCountriesSuccess(){

        val schoolPerformance= SchoolPerformance("0n12345","50","100",
            "80","60")
        val schoolsList= arrayListOf(schoolPerformance)
        singleTest= Single.just(schoolsList)
        Mockito.`when`(schoolService.getSchoolDetails("0n12345")).thenReturn(singleTest)
        schoolPerformanceViewModel.fetchSchoolPerfomrance("0n12345")

        Assert.assertEquals(1,schoolPerformanceViewModel.schoolsPerformance.value?.size)
        Assert.assertEquals(false,schoolPerformanceViewModel.school_details_load_error.value)
        Assert.assertEquals(false,schoolPerformanceViewModel.loading_details.value)
    }

    @Test
    fun getFailure(){
        singleTest= Single.error(Throwable())
        Mockito.`when`(schoolService.getSchoolDetails("0n12345")).thenReturn(singleTest)
        schoolPerformanceViewModel.fetchSchoolPerfomrance("0n12345")
        Assert.assertEquals(true,schoolPerformanceViewModel.school_details_load_error.value)
        Assert.assertEquals(false,schoolPerformanceViewModel.loading_details.value)
    }
}