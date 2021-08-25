package com.example.a20210825_junaidahmed_nycschools.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a20210825_junaidahmed_nycschools.R
import com.example.a20210825_junaidahmed_nycschools.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: SchoolViewModel
    private val schoolListAdapter= SchoolListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel= ViewModelProviders.of(this).get(SchoolViewModel::class.java)
        viewModel.refreshData()

        recyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=schoolListAdapter
        }
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing=false
            viewModel.refreshData()
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.schools.observe(this, Observer {
            it?.let {
                recyclerView.visibility= View.VISIBLE
                schoolListAdapter.updateSchool(it)
            }
        })

        viewModel.school_load_error.observe(this, Observer {
            it?.let {
                list_error.visibility=if(it) View.VISIBLE else View.GONE
            }

        })

        viewModel.loading.observe(this, Observer {
            it?.let {
                loadingIndicator.visibility= if(it) View.VISIBLE else View.GONE
                if (it) {
                    list_error.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
            }
        })
    }
}