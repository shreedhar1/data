package com.example.todolist.Task

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import androidx.cardview.widget.CardView
import com.google.firebase.database.DatabaseReference
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.content.Intent

import android.text.format.DateFormat
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.example.todolist.MainActivity

import com.example.todolist.R

import java.util.*

class Add_Task_activity : AppCompatActivity() {
    lateinit var edtask: EditText
    lateinit var eddate: TextInputEditText
    lateinit var edtime: TextInputEditText
    lateinit var save: CardView
    lateinit var taskreff: DatabaseReference
    lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_Dark)
        } else {
            setTheme(R.style.Theme_Light)
        }
        setContentView(R.layout.activity_add_task)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_back_lay)
        setSupportActionBar(toolbar)
        back = findViewById(R.id.image)
        back.setVisibility(View.VISIBLE)
        back.setOnClickListener(View.OnClickListener { onBackPressed() })
        edtask = findViewById(R.id.edtask)
        eddate = findViewById(R.id.eddate)
        edtime = findViewById(R.id.edtime)
        save = findViewById(R.id.Save_btn)
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val min = calendar[Calendar.MINUTE]
        eddate.setOnClickListener(View.OnClickListener {
            val dialog =
                DatePickerDialog(this@Add_Task_activity, { datePicker, year, month, dayofmonth ->
                    var month = month
                    month = month + 1
                    val date = "$day/$month/$year"
                    eddate.setText(date)
                }, year, month, day)
            dialog.show()
        })
        edtime.setOnClickListener(View.OnClickListener {
            val timePickerDialog =
                TimePickerDialog(this@Add_Task_activity, { timePicker, hour, min ->
                    val calendar1 = Calendar.getInstance()
                    calendar1[Calendar.HOUR] = hour
                    calendar1[Calendar.MINUTE] = min
                    val ampm: String
                    ampm = if (hour >= 12) {
                        "PM"
                    } else {
                        "AM"
                    }
                    val charSequence = DateFormat.format("hh:mm", calendar1)
                    edtime.setText("$charSequence $ampm")
                }, hour, min, false)
            timePickerDialog.show()
        })
        val suid = FirebaseAuth.getInstance().currentUser!!.uid
        taskreff = FirebaseDatabase.getInstance().getReference("Task").child(suid)
        save.setOnClickListener(View.OnClickListener {
            val stask = edtask.getText().toString().trim { it <= ' ' }
            val sdate = eddate.getText().toString().trim { it <= ' ' }
            val stime = edtime.getText().toString().trim { it <= ' ' }
            val id : String

            if (stask == "") {
                Toast.makeText(this@Add_Task_activity, "Can't save empty task", Toast.LENGTH_SHORT)
                    .show()
            } else {
                id = taskreff.push().key.toString()
                val task_pojo = Task_Pojo()
                task_pojo.task = stask
                task_pojo.date = sdate
                task_pojo.time = stime
                task_pojo.uid = suid
                task_pojo.id = id

                taskreff.child(id).setValue(task_pojo)
                Toast.makeText(
                    this@Add_Task_activity,
                    "Task Saved Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                edtask.setText("")
                eddate.setText("")
                edtime.setText("")
            }
        })
    }

    override fun onBackPressed() {
        val i = Intent(this@Add_Task_activity, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}