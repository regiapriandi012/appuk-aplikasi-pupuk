package com.kofar.appuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.kofar.appuk.data_theme.SettingModel
import com.kofar.appuk.data_theme.SettingPreference
import com.kofar.appuk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var settingModel: SettingModel
    private lateinit var mSettingPreference: SettingPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.act = this
        setContentView(binding.root)
        mSettingPreference = SettingPreference(this)
        showExistingPreference()

        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(this, LoginRegister::class.java)
            startActivity(intent)
        }
    }

    private fun showExistingPreference() {
        settingModel = mSettingPreference.getSetting()
        populateView(settingModel)
    }
    private fun populateView(settingModel: SettingModel) {
        if (settingModel.isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            delegate.applyDayNight()
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            delegate.applyDayNight()
        }
        binding.settingModel = settingModel
    }

    companion object {
        private const val REQUEST_CODE = 100
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == SettingPreferenceActivity.RESULT_CODE) {
                showExistingPreference()
            }
        }
    }

}