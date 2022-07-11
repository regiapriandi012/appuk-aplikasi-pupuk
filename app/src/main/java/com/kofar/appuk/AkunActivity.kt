package com.kofar.appuk

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kofar.appuk.addupdate.ArtikelAddUpdateActivity
import com.kofar.appuk.addupdate.PupukAddUpdateActivity
import com.kofar.appuk.listdataedited.ListDataArtikelEditedActivity
import com.kofar.appuk.listdataedited.ListDataPupukEditedActivity


class AkunActivity : AppCompatActivity() {

    companion object {
        private const val SMS_REQUEST_CODE = 101
    }

    // declare the GoogleSignInClient
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_akun)
        supportActionBar?.hide()

        findViewById<Button>(R.id.btn_permission).setOnClickListener{
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS),
                    SMS_REQUEST_CODE)
            }
        }

        findViewById<Button>(R.id.setting_theme).setOnClickListener {
            val intent = Intent(this, SettingPreferenceActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }

        findViewById<Button>(R.id.upload_blog).setOnClickListener {
            val intent = Intent(this, ArtikelAddUpdateActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
            startActivityForResult(intent, com.kofar.appuk.helper.artikelhelper.REQUEST_ADD)
        }

        findViewById<Button>(R.id.upload_produk).setOnClickListener {
            val intent = Intent(this@AkunActivity, PupukAddUpdateActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivityForResult(intent, com.kofar.appuk.helper.pupukhelper.REQUEST_ADD)
        }

        findViewById<ImageView>(R.id.my_blog).setOnClickListener {
            val intent = Intent(this, ListDataArtikelEditedActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }

        findViewById<Button>(R.id.my_pupuk).setOnClickListener {
            val intent = Intent(this, ListDataPupukEditedActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
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
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0, 0);
                Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }

        findViewById<ImageView>(R.id.tombol_kembali_akun).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
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

    override fun onBackPressed() {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults:
    IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_REQUEST_CODE) {
            when {
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
