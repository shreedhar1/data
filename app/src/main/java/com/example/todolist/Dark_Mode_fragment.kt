package com.example.todolist

import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.example.todolist.R
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.app.AppCompatDelegate
import android.widget.CompoundButton
import androidx.fragment.app.Fragment

class Dark_Mode_fragment : Fragment() {
    lateinit var text: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dark_mode, container, false)
        text = view.findViewById(R.id.Textdarkmode)
        val switchCompat = view.findViewById<SwitchCompat>(R.id.dark_mode)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            switchCompat.isChecked = true
            text.setText("Switch to Day Mode")
        } else {
            switchCompat.isChecked = false
        }
        switchCompat.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        return view
    }
}