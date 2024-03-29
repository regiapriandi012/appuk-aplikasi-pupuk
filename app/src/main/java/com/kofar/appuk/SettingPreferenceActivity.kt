package com.kofar.appuk

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kofar.appuk.data_theme.SettingModel
import com.kofar.appuk.data_theme.SettingPreference
import com.kofar.appuk.databinding.ActivitySettingPreferenceBinding

class SettingPreferenceActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mSettingPreference: SettingPreference
    private lateinit var settingModel: SettingModel
    private lateinit var binding: ActivitySettingPreferenceBinding

    companion object {
        const val RESULT_CODE = 101
    }

    private fun showPreferenceInForm() {
        if (settingModel.isDarkTheme) {
            binding.rbYes.isChecked = true
        } else {
            binding.rbNo.isChecked = true
        }
    }

    private fun saveSetting(isDark: Boolean) {
        val settingPreference = SettingPreference(this)
        settingModel.isDarkTheme = isDark
        settingPreference.setSetting(settingModel)
        Toast.makeText(this, "Change Theme", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_setting_preference)

        binding = ActivitySettingPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.saveThemeButton.setOnClickListener(this)
        mSettingPreference = SettingPreference(this)
        settingModel = mSettingPreference.getSetting()
        showPreferenceInForm()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<ImageView>(R.id.tombol_kembali_tema).setOnClickListener {
            val intent = Intent(this, AkunActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }

    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.save_theme_button) {
            val isDark = binding.rgLoveMu.checkedRadioButtonId == R.id.rb_yes
            saveSetting(isDark)
            val resultIntent = Intent()
            setResult(RESULT_CODE, resultIntent)
            overridePendingTransition(0, 0);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            finish()
        }
    }

    override fun onBackPressed() {
    }
}