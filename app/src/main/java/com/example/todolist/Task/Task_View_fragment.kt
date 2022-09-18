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
import androidx.fragment.app.Fragment
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import java.util.ArrayList

class Task_View_fragment : Fragment() {
    lateinit var task_rec: RecyclerView
    lateinit var adaptor: Task_Adaptor
    lateinit var task_pojo: MutableList<Task_Pojo?>
    lateinit var task_ref: DatabaseReference
    var user = FirebaseAuth.getInstance().currentUser!!.uid
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_task_fragment, container, false)
        task_rec = view.findViewById(R.id.task_recycleview)
        task_rec.setHasFixedSize(true)
        task_rec.setLayoutManager(LinearLayoutManager(context))
        task_pojo = ArrayList()
        val preferences = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        val uid = preferences.getString("uid", "")
        val editorr = preferences.edit()
        editorr.apply()
        if (uid == "") {
            task_ref = FirebaseDatabase.getInstance().reference.child("Task").child(user)
            editorr.clear()
            editorr.apply()
        } else {
            task_ref = FirebaseDatabase.getInstance().reference.child("Task").child(uid!!)
            editorr.clear()
            editorr.apply()
        }
        task_ref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        val upload = postSnapshot.getValue(Task_Pojo::class.java)
                        task_pojo.add(upload)
                    }
                    adaptor = Task_Adaptor(activity, task_pojo)
                    task_rec.setAdapter(adaptor)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        return view
    }
}