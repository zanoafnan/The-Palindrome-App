package com.zanoafnan.palindromeapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.core.view.WindowCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        setContentView(R.layout.activity_main)



        val checkButton = findViewById<Button>(R.id.checkButton)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val nameEditText = findViewById<EditText>(R.id.nameTextField)
        val palindromeEditText = findViewById<EditText>(R.id.palindromeTextField)


        nextButton.setOnClickListener {
            val name = nameEditText.text.toString()
            if (name != "") {

                val sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("name", name)
                editor.apply()


                intent = Intent(this, SecondActivity::class.java)
                //intent.putExtra("name", name)
                startActivity(intent)
            } else {
                showAlertDialog(this, "Alert", "Please fill in the name field")
            }

        }

        checkButton.setOnClickListener {
            val input = palindromeEditText.text.toString()
            if (input != "") {
                if (isPalindrome(input)) {
                    showAlertDialog(this, "Palindrome Check", "$input is a palindrome")
                } else {
                    showAlertDialog(this, "Palindrome Check", "$input is not a palindrome")
                }
            } else {
                showAlertDialog(this, "Palindrome Check", "Please fill in the palindrome field")
            }


        }
    }

    fun isPalindrome(input: String): Boolean {
        // Menghilangkan spasi dan mengubah ke huruf kecil agar tidak sensitive terhadap huruf besar/kecil
        val cleanInput = input.lowercase(Locale.ROOT).replace("\\s".toRegex(), "")

        // Membandingkan input dengan kebalikannya
        return cleanInput == cleanInput.reversed()
    }

    private fun showAlertDialog(context: Context, title: String, message: String) {
        // Buat instance AlertDialog.Builder
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)

        // Atur tombol positif
        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
        }

        // Tampilkan dialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}