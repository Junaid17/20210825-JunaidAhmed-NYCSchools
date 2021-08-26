package com.example.a20210825_junaidahmed_nycschools.view


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a20210825_junaidahmed_nycschools.R
import com.example.a20210825_junaidahmed_nycschools.model.School
import kotlinx.android.synthetic.main.item_school_name.view.*

class SchoolListAdapter(var context: Context ,var school:ArrayList<School>):RecyclerView.Adapter<SchoolListAdapter.SchooolViewHolder>() {


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

        //onItem click

        holder.itemView.setOnClickListener(View.OnClickListener {
            val selection = school.get(position)

            // Pasing DBN and school name
            val intent = Intent(context, SchoolDetailsActivity::class.java)
            intent.putExtra("DBN", selection.dbn)
            intent.putExtra("NAME", selection.school_name)
            context.startActivity(intent)
        })
    }


    override fun getItemCount()=school.size


    class SchooolViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val schoolName=view.schoolName
        val schoolLocation=view.schoolLocation

        fun onBind(school: School){
            schoolName.text=school.school_name
            // Removing Lat and Long from location
            var location = school.location?.replace("\\(.*\\)".toRegex(), "")
            schoolLocation.text=location
        }

    }
}