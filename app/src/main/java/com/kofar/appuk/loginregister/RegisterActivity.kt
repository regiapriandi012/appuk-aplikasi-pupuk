package com.kofar.appuk.loginregister

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.kofar.appuk.R
import com.kofar.appuk.WelcomeActivity

class RegisterActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        findViewById<Button>(R.id.register_button).setOnClickListener {
            val nama = findViewById<EditText>(R.id.nama).text.toString()
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password1 = findViewById<EditText>(R.id.password1).text.toString()
            val password2 = findViewById<EditText>(R.id.password2).text.toString()
            if (nama.isEmpty()) {
                findViewById<EditText>(R.id.nama).error = "Nama Kosong"
                return@setOnClickListener
            } else if (username.isEmpty()) {
                findViewById<EditText>(R.id.username).error = "Username Kosong"
                return@setOnClickListener
            } else if (password1.isEmpty()) {
                findViewById<EditText>(R.id.password1).error = "Password Kosong"
                return@setOnClickListener
            } else if (password2.isEmpty()) {
                findViewById<EditText>(R.id.password2).error = "Password Kosong"
                return@setOnClickListener
            }

            val moveWithDataIntent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
            moveWithDataIntent.putExtra(WelcomeActivity.EXTRA_NAMA, nama)
            startActivity(moveWithDataIntent)
        }
    }
}