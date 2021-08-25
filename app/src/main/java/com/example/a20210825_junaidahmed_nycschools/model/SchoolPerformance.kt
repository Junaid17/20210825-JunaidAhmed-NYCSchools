package com.example.a20210825_junaidahmed_nycschools.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class SchoolPerformance(
     val dbn: String?,

     @SerializedName("num_of_sat_test_takers")
     val numOfSatTestTakers: String?,

     @SerializedName("sat_critical_reading_avg_score")
     val satCriticalReadingAvgScore: String?,

     @SerializedName("sat_math_avg_score")
     val satMathAvgScore: String?,

     @SerializedName("sat_writing_avg_score")
     val satWritingAvgScore: String?)
