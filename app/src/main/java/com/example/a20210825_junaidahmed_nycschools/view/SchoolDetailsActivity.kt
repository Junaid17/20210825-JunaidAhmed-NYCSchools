package com.example.a20210825_junaidahmed_nycschools.view

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.a20210825_junaidahmed_nycschools.R
import com.example.a20210825_junaidahmed_nycschools.viewmodel.SchoolPerformanceViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_school_details.*

class SchoolDetailsActivity : AppCompatActivity() {
    lateinit var viewModel: SchoolPerformanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_details)
        viewModel= ViewModelProviders.of(this).get(SchoolPerformanceViewModel::class.java)
        toolbar.title="SAT Score of School"
        toolbar.getNavigationIcon()?.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener { view -> onBackPressed() }

        val intent = intent
        val dbn = intent.getStringExtra("DBN")
        val schoolName = intent.getStringExtra("NAME")
        schoolNameTV.text=schoolName
        viewModel.fetchSchoolPerfomrance(dbn)
        observeViewModel()
    }


    /*
       * Observe data from viewmodel and updating the UI
       * */
    private fun observeViewModel() {
        viewModel.schoolsPerformance.observe(this, Observer {
            it?.let {
                if(it.isEmpty()) {
                    testTakerTV.visibility = View.GONE
                    readingAvgTV.visibility = View.GONE
                    mathsAvgTV.visibility = View.GONE
                    writingAvgTV.visibility = View.GONE
                    noDataTV.text = "No SAT data avaialble for ths school"
                }
                else{
                    testTakerTV.text = "Test takers = ${it.get(0).numOfSatTestTakers}"
                    readingAvgTV.text ="Reading Average Score = ${it.get(0).satCriticalReadingAvgScore}"
                    mathsAvgTV.text ="Maths Average Score = ${it.get(0).satMathAvgScore}"
                    writingAvgTV.text ="Writing Average Score = ${it.get(0).satWritingAvgScore}"
                }
            }
        })

        viewModel.school_details_load_error.observe(this, Observer {
            it?.let {
                list_details_error.visibility=if(it) View.VISIBLE else View.GONE
            }

        })

        viewModel.loading_details.observe(this, Observer {
            it?.let {
                loadingIndicator_details.visibility= if(it) View.VISIBLE else View.GONE
                if (it) {
                    list_details_error.visibility = View.GONE
                }
            }
        })
    }



}
