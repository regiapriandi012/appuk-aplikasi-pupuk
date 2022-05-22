package com.kofar.appuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kofar.appuk.adapter.DataArtikelAdapter

class ListDataArtikel : AppCompatActivity() {
    private val list = ArrayList<DataArtikel>()

    fun getListMyDatas(): ArrayList<DataArtikel> {
        val judulArtikel = resources.getStringArray(R.array.judul_artikel)
        val gambarArtikel = resources.getStringArray(R.array.foto_artikel)
        val tanggalPublishArtikel = resources.getStringArray(R.array.tanggal_artikel_dipublish)
        val paragrafSatu = resources.getStringArray(R.array.paragraf_satu)
        val listMyData = ArrayList<DataArtikel>()
        for (position in judulArtikel.indices) {
            val myData = DataArtikel(
                judulArtikel[position],
                gambarArtikel[position],
                tanggalPublishArtikel[position],
                paragrafSatu[position],
            )
            listMyData.add(myData)
        }
        return listMyData
    }

    private fun showRecyclerList() {
        findViewById<RecyclerView>(R.id.rv_data_artikel).layoutManager = LinearLayoutManager(this)
        val listMyDataAdapter = DataArtikelAdapter(list)
        findViewById<RecyclerView>(R.id.rv_data_artikel).adapter = listMyDataAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data_artikel)
        supportActionBar?.hide()

        findViewById<RecyclerView>(R.id.rv_data_artikel).setHasFixedSize(true)
        list.addAll(getListMyDatas())
        showRecyclerList()

        findViewById<Button>(R.id.tombol_belanja_pupuk_artikel).setOnClickListener {
            val intent = Intent(this, ListDataPupuk::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.tombol_akun_artikel).setOnClickListener {
            val intent = Intent(this, AkunActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.tombol_kembali_artikel).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.tombol_artikel_artikel).setOnClickListener {
            val intent = Intent(this, ListDataArtikel::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.tombol_keranjang_belanja_artikel).setOnClickListener {
            val intent = Intent(this, ListDataPupuk::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.tombol_home_artikel).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}