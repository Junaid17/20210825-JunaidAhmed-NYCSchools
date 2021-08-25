package com.example.a20210825_junaidahmed_nycschools.view

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

        // Extracting data received through the intent
//        val intent = intent
        val dbn = intent.getStringExtra("DBN")
        val schoolName = intent.getStringExtra("NAME")
        viewModel.fetchSchoolPerfomrance(dbn)
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.schoolsPerformance.observe(this, Observer {
            it?.let {
                if(it.isEmpty())
                    textview.text="No Score avaialble for ths school"
                else
                    textview.text=it.toString()
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
