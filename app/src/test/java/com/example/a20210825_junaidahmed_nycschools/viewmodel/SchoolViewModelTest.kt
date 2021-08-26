package com.example.a20210825_junaidahmed_nycschools.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.a20210825_junaidahmed_nycschools.model.School
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

class SchoolViewModelTest {

    @get:Rule
    val rule= InstantTaskExecutorRule()

    @Mock
    lateinit var schoolService: SchoolService

    @InjectMocks
    var schoolViewModel=SchoolViewModel()

    private var singleTest: Single<List<School>>?=null


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

        val schools=School("0n12345","Don Bosco","India")
        val schoolsList= arrayListOf(schools)
        singleTest= Single.just(schoolsList)
        Mockito.`when`(schoolService.getSchools()).thenReturn(singleTest)
        schoolViewModel.refreshData()

        Assert.assertEquals(1,schoolViewModel.schools.value?.size)
        Assert.assertEquals(false,schoolViewModel.school_load_error.value)
        Assert.assertEquals(false,schoolViewModel.loading.value)
    }

    @Test
    fun getFailure(){
        singleTest= Single.error(Throwable())
        Mockito.`when`(schoolService.getSchools()).thenReturn(singleTest)
        schoolViewModel.refreshData()
        Assert.assertEquals(true,schoolViewModel.school_load_error.value)
        Assert.assertEquals(false,schoolViewModel.loading.value)
    }
}