package com.kofar.appuk

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailArtikelActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA_ARTIKEL = "extra_data_artikel"
    }

    inline fun <reified T : Parcelable> Activity.getParcelableExtra(key: String) = lazy {
        intent.getParcelableExtra<T>(key)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.detail_artikel_activity)

        val dataArtikelDetail by getParcelableExtra<DataArtikel>(EXTRA_DATA_ARTIKEL)

        Glide.with(this)
            .load(dataArtikelDetail?.gambar_artikel.toString())
            .apply(RequestOptions().override(700, 700))
            .into(findViewById(R.id.gambar_artikel_detail))
        findViewById<TextView>(R.id.judul_artikel_detail).text = dataArtikelDetail?.judul_artikel.toString()
        findViewById<TextView>(R.id.paragraf_satu).text = dataArtikelDetail?.paragraf_satu.toString()

        findViewById<Button>(R.id.tombol_kembali_artikel_detail).setOnClickListener {
            val intent = Intent(this, ListDataArtikel::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.tombol_akun_artikel_detail).setOnClickListener {
            val intent = Intent(this, AkunActivity::class.java)
            startActivity(intent)
        }
    }
}