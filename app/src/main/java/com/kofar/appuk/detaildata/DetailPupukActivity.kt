package com.kofar.appuk.detaildata

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kofar.appuk.AkunActivity
import com.kofar.appuk.R
import com.kofar.appuk.data.DataPupuk
import com.kofar.appuk.listdata.ListDataPupuk

class DetailPupukActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA_PUPUK = "extra_data_pupuk"
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
        setContentView(R.layout.detail_pupuk_activity)
        val myData by getParcelableExtra<DataPupuk>(EXTRA_DATA_PUPUK)

        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO;

        Glide.with(this)
            .load(myData?.gambar_pupuk.toString())
            .apply(RequestOptions().override(700, 700))
            .into(findViewById(R.id.gambar_pupuk_detail))
        findViewById<TextView>(R.id.nama_pupuk_produk).text = myData?.nama_pupuk.toString()
        findViewById<TextView>(R.id.harga_pupuk_detail).text = myData?.harga_pupuk.toString()
        findViewById<TextView>(R.id.keterangan_pupuk).text = myData?.keterangan_pupuk.toString()

        findViewById<ImageView>(R.id.tombol_kembali_pupuk_detail).setOnClickListener {
            val intent = Intent(this, ListDataPupuk::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }

        findViewById<ImageView>(R.id.tombol_akun_pupuk_detail).setOnClickListener {
            val intent = Intent(this, AkunActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }
    }

    override fun onBackPressed() {
    }
}