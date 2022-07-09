package com.kofar.appuk.detaildata

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kofar.appuk.AkunActivity
import com.kofar.appuk.listdata.ListDataArtikel
import com.kofar.appuk.R
import com.kofar.appuk.data.DataArtikel

class DetailArtikelActivity : AppCompatActivity() {

    private var artikel: DataArtikel? = null

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

        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO;

        setContentView(R.layout.detail_artikel_activity)

        val dataArtikelDetail by getParcelableExtra<DataArtikel>(EXTRA_DATA_ARTIKEL)

        Glide.with(this)
            .load(dataArtikelDetail?.gambar_artikel.toString())
            .apply(RequestOptions().override(700, 700))
            .into(findViewById(R.id.gambar_artikel_detail))
        findViewById<TextView>(R.id.judul_artikel_detail).text = dataArtikelDetail?.judul_artikel.toString()
        findViewById<TextView>(R.id.penulis_artikel).text = "By " + dataArtikelDetail?.penulis_artikel.toString()
        findViewById<TextView>(R.id.tanggal_publish_artikel_detail).text = dataArtikelDetail?.tanggal_publish.toString()
        findViewById<TextView>(R.id.paragraf_satu).text = dataArtikelDetail?.paragraf_satu.toString()
        findViewById<TextView>(R.id.paragraf_dua).text = dataArtikelDetail?.paragraf_dua.toString()
        findViewById<TextView>(R.id.paragraf_tiga).text = dataArtikelDetail?.paragraf_tiga.toString()
        findViewById<TextView>(R.id.paragraf_empat).text = dataArtikelDetail?.paragraf_empat.toString()
        findViewById<TextView>(R.id.paragraf_lima).text = dataArtikelDetail?.paragraf_lima.toString()

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