package com.kofar.appuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kofar.appuk.adapter.DataPupukAdapter

class ListDataPupuk : AppCompatActivity() {
    private val list = ArrayList<DataPupuk>()

    private fun showRecyclerGrid() {
        findViewById<RecyclerView>(R.id.rv_data_pupuk).layoutManager = GridLayoutManager(this, 2)
        val dataPupukAdapter = DataPupukAdapter(list, this)
        findViewById<RecyclerView>(R.id.rv_data_pupuk).adapter = dataPupukAdapter
    }

    fun getListMyDatas(): ArrayList<DataPupuk> {
        val dataNamaPupuk = resources.getStringArray(R.array.data_nama_pupuk)
        val dataHargaPupuk = resources.getStringArray(R.array.data_harga_pupuk)
        val dataGambarPupuk = resources.getStringArray(R.array.data_gambar_pupuk)
        val dataKeteranganPupuk = resources.getStringArray(R.array.data_keterangan_pupuk)
        val listDatumPupuks = ArrayList<DataPupuk>()
        for (position in dataNamaPupuk.indices) {
            val dataPupuk = DataPupuk(
                dataNamaPupuk[position],
                dataHargaPupuk[position],
                dataGambarPupuk[position],
                dataKeteranganPupuk[position]
            )
            listDatumPupuks.add(dataPupuk)
        }
        return listDatumPupuks
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data_pupuk)
        supportActionBar?.hide()

        findViewById<RecyclerView>(R.id.rv_data_pupuk).setHasFixedSize(true)
        list.addAll(getListMyDatas())
        showRecyclerGrid()

        findViewById<Button>(R.id.tombol_belanja_pupuk).setOnClickListener {
            val intent = Intent(this, ListDataPupuk::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.tombol_akun).setOnClickListener {
            val intent = Intent(this, AkunActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.tombol_kembali).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.tombol_artikel).setOnClickListener {
            val intent = Intent(this, ListDataArtikel::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.tombol_keranjang_belanja).setOnClickListener {
            val intent = Intent(this, ListDataPupuk::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.tombol_home).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

}