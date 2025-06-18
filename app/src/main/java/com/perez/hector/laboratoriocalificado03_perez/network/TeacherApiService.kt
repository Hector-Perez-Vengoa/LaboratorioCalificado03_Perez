package com.perez.hector.laboratoriocalificado03_perez.network

import com.perez.hector.laboratoriocalificado03_perez.model.Teacher
import retrofit2.Call
import retrofit2.http.GET

interface TeacherApiService {
    @GET("list/teacher")
    fun getTeachers(): Call<TeacherResponse>
}

data class TeacherResponse(
    val teachers: List<Teacher>
)
