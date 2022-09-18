package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.widget.EditText
import android.os.Bundle
import com.google.firebase.auth.FirebaseUser
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var login_btn: CardView
    private lateinit var T_register: TextView
    private lateinit var T_for_pass: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var ed_emailid: EditText
    private lateinit var ed_pass: EditText
    private var email = ""
    private var pass = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        login_btn = findViewById(R.id.Login_btn)
        T_register = findViewById(R.id.register_page)
        T_for_pass = findViewById(R.id.forget_password)
        ed_emailid = findViewById(R.id.email)
        ed_pass = findViewById(R.id.password)
        login_btn.setOnClickListener(View.OnClickListener {
            email = ed_emailid.getText().toString().trim { it <= ' ' }
            pass = ed_pass.getText().toString().trim { it <= ' ' }
            if (email == "" || pass == "") {
                Toast.makeText(this@Login, "Enter Email id and Password", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this@Login) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            updateUI(user)
                            Toast.makeText(this@Login, "Login successful", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this@Login, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                this@Login, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            Toast.makeText(this@Login, "Login failed", Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }

                        // ...
                    }
            }
        })
        T_register.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
            finish()
        })
    }

    private fun updateUI(user: FirebaseUser?) {}

    companion object {
        private const val TAG = "EmailPassword"
    }
}