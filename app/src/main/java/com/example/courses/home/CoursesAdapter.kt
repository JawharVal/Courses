package com.example.courses.home

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.courses.R
import com.example.courses.databinding.ItemCourseBinding
import com.example.courses.domain.model.Course
class CourseAdapter(
    private val onLike: (Course) -> Unit
) : ListAdapter<Course, CourseAdapter.CourseViewHolder>(CourseDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = getItem(position)
        holder.bind(course)
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root) {


        private val courseTitle: TextView = binding.courseTitle
        private val courseDescription: TextView = binding.courseDescription
        private val coursePrice: TextView = binding.coursePrice
        private val courseRating: TextView = binding.courseRating
        private val btnLike: ImageView = binding.btnLike

        fun bind(course: Course) {
            // Bind the data to the views
            courseTitle.text = course.title
            courseDescription.text = course.text
            coursePrice.text = "${course.price} ₽"
            courseRating.text = course.rate.toString()


            if (courseDescription.lineCount > 2) {
                courseDescription.maxLines = 2
                courseDescription.ellipsize = TextUtils.TruncateAt.END
            }


            Glide.with(binding.root.context)
                .load(course.imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.courseImage)



            btnLike.setOnClickListener {
                onLike(course)
            }
        }
    }


    class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }
}
