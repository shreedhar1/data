package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.todolist.R
import android.content.Intent
import com.example.todolist.Task.Add_Task_activity
import com.google.android.material.navigation.NavigationBarView
import com.example.todolist.Users.Users_fragment
import com.example.todolist.Task.Task_View_fragment
import com.example.todolist.Dark_Mode_fragment
import android.content.DialogInterface
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.todolist.Login
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var addtask_btn: FloatingActionButton
    lateinit var fragment: Fragment
    var manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_Dark)
        } else {
            setTheme(R.style.Theme_Light)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_back_lay)
        setSupportActionBar(toolbar)
        addtask_btn = findViewById(R.id.addtask_btn)
        bottomNavigationView = findViewById(R.id.bot_nav)
        bottomNavigationView.setOnItemSelectedListener(navListener)
        addtask_btn.setOnClickListener(View.OnClickListener {
            val i = Intent(this@MainActivity, Add_Task_activity::class.java)
            startActivity(i)
            finish()
        })
        showhome()
    }

    private val navListener = NavigationBarView.OnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                fragment = Users_fragment()
                manager.beginTransaction().replace(R.id.f_container, fragment).addToBackStack(null)
                    .commit()
            }
            R.id.Task -> {
                fragment = Task_View_fragment()
                manager.beginTransaction().replace(R.id.f_container, fragment, "MyFragment")
                    .addToBackStack(null).commit()
            }
            R.id.dark_mode -> {
                fragment = Dark_Mode_fragment()
                manager.beginTransaction().replace(R.id.f_container, fragment, "MyFragment")
                    .addToBackStack(null).commit()
            }
            R.id.logout -> AlertDialog.Builder(this@MainActivity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Do you want to Logout?")
                .setPositiveButton("Yes") { dialog, which ->

                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(applicationContext, "Logout Successful", Toast.LENGTH_SHORT)
                        .show()

                    val intent1 = Intent(this@MainActivity, Login::class.java)
                    startActivity(intent1)
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        }
        true
    }

    override fun onBackPressed() {
        val myFragment = supportFragmentManager.findFragmentByTag("MyFragment")
        if (myFragment != null && myFragment.isVisible) {
            showhome()
        } else {
            AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes) { arg0, arg1 -> super@MainActivity.getOnBackPressedDispatcher() }
                .create().show()
        }
    }

    private fun showhome() {
        fragment = Users_fragment()
        manager.beginTransaction().replace(R.id.f_container, fragment, fragment.getTag())
            .addToBackStack(null).commit()
    }
}