package com.kofar.appuk.loginregister

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.kofar.appuk.R
import com.kofar.appuk.WelcomeActivity
import com.kofar.appuk.data_user.UserModel
import com.kofar.appuk.data_user.UserPreference
import com.kofar.appuk.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userModel: UserModel
    private lateinit var mSettingPreference: UserPreference
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.act = this
        setContentView(binding.root)
        supportActionBar?.title = ""
        mSettingPreference = UserPreference(this)
        showExistingPreference()

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val username = findViewById<EditText>(R.id.username_login).text.toString()
            val password = findViewById<EditText>(R.id.password_login).text.toString()

            if (username.isEmpty()) {
                findViewById<EditText>(R.id.username_login).error = "Username Kosong"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                findViewById<EditText>(R.id.password_login).error = "Password Kosong"
                return@setOnClickListener
            }
            if (username != mSettingPreference.getSetting().username){
                findViewById<EditText>(R.id.username_login).error = "Username Tidak Ada"
                return@setOnClickListener
            } else if (password != mSettingPreference.getSetting().password) {
                findViewById<EditText>(R.id.password_login).error = "Password Salah"
                return@setOnClickListener
            }

            val moveWithDataIntent = Intent(this, WelcomeActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            moveWithDataIntent.putExtra(WelcomeActivity.EXTRA_NAMA, userModel.name)
            startActivity(moveWithDataIntent)
        }
    }

    private fun showExistingPreference() {
        userModel = mSettingPreference.getSetting()
        populateView(userModel)
    }
    private fun populateView(userModel: UserModel) {
        binding.userModel = userModel
    }

    companion object {
        private const val REQUEST_CODE = 100
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RegisterActivity.RESULT_CODE) {
                showExistingPreference()
            }
        }
    }
}