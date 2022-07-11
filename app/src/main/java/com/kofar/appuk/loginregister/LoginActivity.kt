package com.kofar.appuk.loginregister

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.kofar.appuk.R
import com.kofar.appuk.WelcomeActivity

class LoginActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        findViewById<Button>(R.id.register_button).setOnClickListener {
            val username = findViewById<EditText>(R.id.nama).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()
            if (username.isEmpty()) {
                findViewById<EditText>(R.id.nama).error = "Username Kosong"
                return@setOnClickListener
            } else if (password.isEmpty()) {
                findViewById<EditText>(R.id.password).error = "Password Kosong"
                return@setOnClickListener
            }

            val moveWithDataIntent = Intent(this, WelcomeActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            moveWithDataIntent.putExtra(WelcomeActivity.EXTRA_NAMA, username)
            startActivity(moveWithDataIntent)
        }
    }
}