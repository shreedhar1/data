package com.example.todolist.Task

import com.example.todolist.Task.Task_Pojo
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.todolist.R
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import androidx.cardview.widget.CardView
import com.google.firebase.database.DatabaseReference
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.DatePicker
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.widget.TimePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
import android.content.Intent
import com.example.todolist.MainActivity
import com.example.todolist.Task.Task_Adaptor
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.SharedPreferences
import android.view.View
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class Task_Adaptor(private val context: Context?, private val uploads: List<Task_Pojo?>) :
    RecyclerView.Adapter<Task_Adaptor.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_design, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val upload = uploads[position]
        holder.task.text = upload!!.task
        holder.date.text = upload!!.date
        holder.time.text = upload!!.time

    }

    override fun getItemCount(): Int {
        return uploads.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var task: TextView
        var date: TextView
        var time: TextView

        init {
            task = itemView.findViewById<View>(R.id.t_task) as TextView
            date = itemView.findViewById<View>(R.id.t_date) as TextView
            time = itemView.findViewById<View>(R.id.t_time) as TextView
        }
    }
}