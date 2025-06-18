package com.perez.hector.laboratoriocalificado03_perez

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.perez.hector.laboratoriocalificado03_perez.adapter.TeacherAdapter
import com.perez.hector.laboratoriocalificado03_perez.databinding.ActivityEjercicio01Binding
import com.perez.hector.laboratoriocalificado03_perez.model.Teacher
import com.perez.hector.laboratoriocalificado03_perez.network.TeacherApiService
import com.perez.hector.laboratoriocalificado03_perez.network.TeacherResponse
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class Ejercicio01Activity : AppCompatActivity() {
    private lateinit var binding: ActivityEjercicio01Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerTeachers.layoutManager = LinearLayoutManager(this)
        getTeachers()
    }

    private fun getTeachers() {
        binding.progressBar.visibility = View.VISIBLE
        val retrofit = Retrofit.Builder()
            .baseUrl("https://private-effe28-tecsup1.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(TeacherApiService::class.java)
        service.getTeachers().enqueue(object : Callback<TeacherResponse> {
            override fun onResponse(call: Call<TeacherResponse>, response: Response<TeacherResponse>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val teachers = response.body()?.teachers ?: emptyList()
                    val adapter = TeacherAdapter(
                        teachers,
                        onItemClick = { teacher -> callTeacher(teacher.phone) },
                        onItemLongClick = { teacher -> sendEmail(teacher.email) }
                    )
                    binding.recyclerTeachers.adapter = adapter
                } else {
                    Toast.makeText(this@Ejercicio01Activity, getString(R.string.error_loading), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<TeacherResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@Ejercicio01Activity, getString(R.string.error_loading), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callTeacher(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        startActivity(intent)
    }

    private fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$email")
        startActivity(intent)
    }
}
