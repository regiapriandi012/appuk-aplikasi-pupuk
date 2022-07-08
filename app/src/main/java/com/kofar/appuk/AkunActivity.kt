package com.kofar.appuk

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kofar.appuk.artikelhelper.REQUEST_ADD
import com.kofar.appuk.data_theme.SettingModel
import com.kofar.appuk.data_theme.SettingPreference
import com.kofar.appuk.databinding.ActivityAkunBinding


class AkunActivity : AppCompatActivity() {

    // declare the GoogleSignInClient
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityAkunBinding
    private lateinit var settingModel: SettingModel
    private lateinit var mSettingPreference: SettingPreference

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_akun)
        supportActionBar?.hide()

        binding = ActivityAkunBinding.inflate(layoutInflater)
        binding.act = this
        setContentView(binding.root)
        mSettingPreference = SettingPreference(this)
        showExistingPreference()

        findViewById<Button>(R.id.setting_theme).setOnClickListener {
            val intent = Intent(this, SettingPreferenceActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.admin).setOnClickListener {
            val intent = Intent(this, ArtikelAddUpdateActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
        }


        // call requestIdToken as follows
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<Button>(R.id.button_logout).setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(this, MainActivity::class.java)
                Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }

        findViewById<Button>(R.id.tombol_kembali_akun).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if(acct != null) {
            val personImage = acct.photoUrl.toString()
            val personEmail = acct.email.toString()
            Glide.with(this)
                .load(personImage)
                .apply(RequestOptions().override(300, 300))
                .into(findViewById(R.id.image_profile))
            findViewById<ImageView>(R.id.image_profile).setImageURI(Uri.parse(personImage))
            findViewById<ImageView>(R.id.image_profile).postInvalidate()
            val personName = acct.displayName
            findViewById<TextView>(R.id.welcome_name_account).text = personName
            findViewById<TextView>(R.id.email_akun).text = personEmail

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

    fun openSetting(){
        val intent = Intent(this, SettingPreferenceActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
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
