package com.perez.hector.laboratoriocalificado03_perez.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.perez.hector.laboratoriocalificado03_perez.databinding.ItemTeacherBinding
import com.perez.hector.laboratoriocalificado03_perez.model.Teacher

class TeacherAdapter(
    private val teachers: List<Teacher>,
    private val onItemClick: (Teacher) -> Unit,
    private val onItemLongClick: (Teacher) -> Unit
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    inner class TeacherViewHolder(val binding: ItemTeacherBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val binding = ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = teachers[position]
        holder.binding.txtName.text = teacher.name
        holder.binding.txtLastName.text = teacher.last_name
        Glide.with(holder.binding.imgTeacher.context)
            .load(teacher.imageUrl)
            .into(holder.binding.imgTeacher)
        holder.itemView.setOnClickListener { onItemClick(teacher) }
        holder.itemView.setOnLongClickListener {
            onItemLongClick(teacher)
            true
        }
    }

    override fun getItemCount(): Int = teachers.size
}
