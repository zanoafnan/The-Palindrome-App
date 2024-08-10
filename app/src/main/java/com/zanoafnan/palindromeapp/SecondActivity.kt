package com.zanoafnan.palindromeapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

class SecondActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_second)


        val nameText = findViewById<TextView>(R.id.tvName)
        val sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        nameText.text = name

        val back = findViewById<ImageButton>(R.id.backButton)
        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        val next = findViewById<Button>(R.id.chooseUserButton)
        next.setOnClickListener{
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
        val selectedUsername = findViewById<TextView>(R.id.selectedUserame)
        val firstName = intent.getStringExtra("firstname")
        val lastName = intent.getStringExtra("lastname")
        if (firstName != null && lastName != null) {
            selectedUsername.text = "$firstName $lastName"
        } else {
            selectedUsername.text = "Selected User Name"
        }
    }
}
