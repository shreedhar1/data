package com.example.todolist.Users

import android.content.Context
import android.content.Intent
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
import com.example.todolist.Login
import com.example.todolist.MainActivity
import java.util.ArrayList

class Users_fragment : Fragment() {
    lateinit var userref: DatabaseReference
    lateinit var current_userref: DatabaseReference
    lateinit var user_adaptor: User_Adaptor
    lateinit  var uploads: MutableList<Pojo_User?>
    lateinit var user_recy: RecyclerView
    lateinit var fullname: TextView
    lateinit var age: TextView
    lateinit var dob: TextView
    lateinit var email: TextView
    lateinit var current_user_card: CardView
    private lateinit var mAuth: FirebaseAuth

    var user = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_users_fragment, container, false)

        fullname = view.findViewById(R.id.u_name)
        age = view.findViewById(R.id.u_age)
        dob = view.findViewById(R.id.u_dob)
        email = view.findViewById(R.id.u_email)
        current_user_card = view.findViewById(R.id.lay_1)
        user_recy = view.findViewById(R.id.users_recycleview)
        user_recy.setHasFixedSize(true)
        user_recy.setLayoutManager(LinearLayoutManager(context))
        uploads = ArrayList()
        current_userref = FirebaseDatabase.getInstance().reference.child("Users").child(user!!)
        current_userref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val txt_name = dataSnapshot.child("fullname").value.toString()
                    val txt_age = dataSnapshot.child("age").value.toString()
                    val txt_dob = dataSnapshot.child("dob").value.toString()
                    val txt_email = dataSnapshot.child("email").value.toString()
                    fullname.setText(txt_name)
                    age.setText(txt_age)
                    dob.setText(txt_dob)
                    email.setText(txt_email)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
            }
        })
        current_user_card.setOnClickListener(View.OnClickListener {
            val pree = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
            val edito = pree.edit()
            edito.putString("uid", user)
            edito.apply()
            val fragment: Fragment = Task_View_fragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.f_container, fragment, "MyFragment")
                .addToBackStack(null)
                .commit()
        })
        userref = FirebaseDatabase.getInstance().reference.child("Users")
        userref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        val upload = postSnapshot.getValue(Pojo_User::class.java)
                        uploads.add(upload)
                    }
                    user_adaptor = User_Adaptor(activity, uploads)
                    user_recy.setAdapter(user_adaptor)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        return view
    }
}