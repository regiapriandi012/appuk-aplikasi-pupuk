package com.kofar.appuk.loginregister

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kofar.appuk.R
import com.kofar.appuk.data_user.UserModel
import com.kofar.appuk.data_user.UserPreference
import com.kofar.appuk.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var userPreference: UserPreference
    private lateinit var settingModel: UserModel
    private lateinit var binding: ActivityRegisterBinding

    companion object {
        const val RESULT_CODE = 101
    }

    private fun showPreferenceInForm() {
        binding.nama.setText(settingModel.name)
        binding.username.setText(settingModel.username)
        binding.password1.setText(settingModel.password)
    }

    private fun saveSetting(name: String, username: String, password: String) {
        val userPreference = UserPreference(this)
        settingModel.name = name
        settingModel.username = username
        settingModel.password = password
        userPreference.setSetting(settingModel)
        Toast.makeText(this, "User Berhasil Dibuat, Silahkan Login", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.registerButton.setOnClickListener(this)
        userPreference = UserPreference(this)
        settingModel = userPreference.getSetting()
        showPreferenceInForm()
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.register_button) {
            val name = binding.nama.text.toString().trim()
            val username = binding.username.text.toString().trim()
            val password = binding.password1.text.toString().trim()
            if (name.isEmpty()) {
                binding.nama.error = "Required"
                return
            }
            if (username.isEmpty()) {
                binding.username.error = "Required"
                return
            }
            if (password.isEmpty()) {
                binding.password1.error = "Required"
                return
            }
            saveSetting(name, username, password)
            val resultIntent = Intent()
            setResult(RESULT_CODE, resultIntent)
            finish()
        }
    }
}