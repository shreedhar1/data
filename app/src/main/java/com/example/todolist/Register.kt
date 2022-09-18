package com.example.todolist


import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.example.todolist.Pojo_User
import com.google.android.material.textfield.TextInputEditText
import android.os.Bundle
import com.example.todolist.R
import com.google.firebase.database.FirebaseDatabase
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.DatePicker
import android.content.Intent
import android.view.View
import com.example.todolist.Login
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.example.todolist.MainActivity
import java.util.*

class Register : AppCompatActivity() {
    private lateinit var reg_btn: CardView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var ed_fullname: EditText
    private lateinit var ed_emailid: EditText
    private lateinit var ed_age: EditText
    private lateinit var ed_pass: EditText
    private lateinit var T_login: TextView
    private var email = ""
    private var pass = ""
    private var fn = ""
    private var age = ""
    private var dob = ""
    private var uid = ""
    private lateinit var reff: DatabaseReference
    private lateinit var pojo_user: Pojo_User
    private lateinit var ed_dob: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_register)
        reff = FirebaseDatabase.getInstance().reference.child("Users")
        T_login = findViewById(R.id.login_page)
        reg_btn = findViewById(R.id.reg_btn)
        ed_fullname = findViewById(R.id.fullname)
        ed_age = findViewById(R.id.age)
        ed_dob = findViewById(R.id.Dob)
        ed_emailid = findViewById(R.id.email)
        ed_pass = findViewById(R.id.password)
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        ed_dob.setOnClickListener(View.OnClickListener {
            val dialog = DatePickerDialog(this@Register, { datePicker, year, month, dayofmonth ->
                var month = month
                month = month + 1
                val date = "$day/$month/$year"
                ed_dob.setText(date)
            }, year, month, day)
            dialog.show()
        })
        T_login.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Register, Login::class.java)
            startActivity(intent)
            finish()
        })
        reg_btn.setOnClickListener(View.OnClickListener {
            fn = ed_fullname.getText().toString().trim { it <= ' ' }
            age = ed_age.getText().toString().trim { it <= ' ' }
            dob = ed_dob.getText().toString().trim { it <= ' ' }
            email = ed_emailid.getText().toString().trim { it <= ' ' }
            pass = ed_pass.getText().toString().trim { it <= ' ' }
            if (email == "" || pass == "" || fn == "" || age == "" || dob == "") {
                Toast.makeText(this@Register, "Fill All details Please", Toast.LENGTH_SHORT).show()
            } else {
                mAuth!!.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this@Register) { task ->
                        if (task.isSuccessful) {
                            val user = mAuth!!.currentUser
                            updateUI(user)
                            uid = FirebaseAuth.getInstance().currentUser!!.uid
                            pojo_user = Pojo_User()
                            pojo_user!!.fullname = fn
                            pojo_user!!.age = age
                            pojo_user!!.dob = dob
                            pojo_user!!.email = email
                            pojo_user!!.uid = uid
                            reff!!.child(uid).setValue(pojo_user)
                            Toast.makeText(
                                this@Register,
                                "registration successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@Register, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@Register,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(null)
                        }
                    }
            }
        })
    }

    private fun updateUI(user: FirebaseUser?) {}

    companion object {
        private const val TAG = "EmailPassword"
    }
}