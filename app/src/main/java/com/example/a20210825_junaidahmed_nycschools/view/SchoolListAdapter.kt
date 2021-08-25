package com.example.a20210825_junaidahmed_nycschools.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a20210825_junaidahmed_nycschools.R
import com.example.a20210825_junaidahmed_nycschools.model.School
import kotlinx.android.synthetic.main.item_school_name.view.*

class SchoolListAdapter(var school:ArrayList<School>):RecyclerView.Adapter<SchoolListAdapter.SchooolViewHolder>() {

    fun updateSchool(newSchool:List<School>){
        school.clear()
        school.addAll(newSchool)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SchooolViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_school_name,parent,false))


    override fun onBindViewHolder(holder: SchooolViewHolder, position: Int) {
        holder.onBind(school[position])
    }

    override fun getItemCount()=school.size

    class SchooolViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val dbn=view.dbn
        val schoolName=view.schoolName

        fun onBind(school: School){
            dbn.text=school.dbn
            schoolName.text=school.school_name
        }

    }
}