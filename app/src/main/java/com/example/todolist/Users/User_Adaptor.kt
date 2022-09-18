package com.example.todolist.Users

import android.content.Context
import com.example.todolist.Pojo_User
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.todolist.R
import android.content.SharedPreferences
import com.example.todolist.Task.Task_View_fragment
import androidx.fragment.app.FragmentActivity
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.example.todolist.Users.User_Adaptor
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.widget.Toast
import androidx.fragment.app.Fragment

class User_Adaptor(private val context: Context?, private val uploads: List<Pojo_User?>) :
    RecyclerView.Adapter<User_Adaptor.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_design, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val upload = uploads[position]
        holder.fullname.text = upload!!.fullname
        holder.age.text = upload.age
        holder.dob.text = upload.dob
        holder.email.text = upload.email
        holder.itemView.setOnClickListener { v ->
            val pree = context!!.getSharedPreferences("User", Context.MODE_PRIVATE)
            val edito = pree.edit()
            val uid = upload.uid
            edito.putString("uid", uid)
            edito.apply()
            val fragment: Fragment = Task_View_fragment()
            (v.context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.f_container, fragment, "MyFragment").addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int {
        return uploads.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fullname: TextView
        var age: TextView
        var dob: TextView
        var email: TextView

        init {
            fullname = itemView.findViewById<View>(R.id.u_name) as TextView
            age = itemView.findViewById<View>(R.id.u_age) as TextView
            dob = itemView.findViewById<View>(R.id.u_dob) as TextView
            email = itemView.findViewById<View>(R.id.u_email) as TextView
        }
    }
}