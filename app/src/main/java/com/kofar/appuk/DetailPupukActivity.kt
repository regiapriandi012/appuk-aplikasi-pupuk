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
import com.kofar.appuk.databinding.ActivityDetailPupukBinding

class DetailPupukActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MYDATA = "extra_mydata"
    }
    inline fun <reified T : Parcelable> Activity.getParcelableExtra(key: String) = lazy {
        intent.getParcelableExtra<T>(key)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private lateinit var binding: ActivityDetailPupukBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_pupuk_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        val myData by getParcelableExtra<DataPupuk>(EXTRA_MYDATA)

        findViewById<TextView>(R.id.nama_pupuk_produk).text = myData?.nama_pupuk.toString()
        findViewById<TextView>(R.id.harga_pupuk_detail).text = myData?.harga_pupuk.toString()
        findViewById<TextView>(R.id.keterangan_pupuk).text = myData?.keterangan_pupuk.toString()
        Glide.with(this)
            .load(myData?.gambar_pupuk.toString())
            .apply(RequestOptions().override(700, 700))
            .into(findViewById(R.id.gambar_pupuk_detail))

        findViewById<Button>(R.id.tombol_kembali2).setOnClickListener {
            val intent = Intent(this, ListDataPupuk::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.tombol_akun2).setOnClickListener {
            val intent = Intent(this, ListDataPupuk::class.java)
            startActivity(intent)
        }
    }
}