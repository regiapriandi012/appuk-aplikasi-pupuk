package com.kofar.appuk

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kofar.appuk.adapter.DataArtikelAdapter
import com.kofar.appuk.artikelhelper.EXTRA_ARTIKEL
import com.kofar.appuk.artikelhelper.EXTRA_POSITION
import com.kofar.appuk.artikelhelper.REQUEST_ADD
import com.kofar.appuk.artikelhelper.REQUEST_UPDATE
import com.kofar.appuk.artikelhelper.RESULT_ADD
import com.kofar.appuk.artikelhelper.RESULT_DELETE
import com.kofar.appuk.artikelhelper.RESULT_UPDATE
import com.kofar.appuk.artikelhelper.mapCursorToArrayList
import com.kofar.appuk.data.DataArtikel
import com.kofar.appuk.databinding.ActivityListDataArtikelBinding
import com.kofar.appuk.db.ArtikelHelper
import kotlinx.android.synthetic.main.activity_list_data_artikel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListDataArtikel : AppCompatActivity() {
    private lateinit var artikelHelper: ArtikelHelper
    private lateinit var artikelAdapter: DataArtikelAdapter
    private val EXTRA_STATE = "EXTRA_STATE"
    private lateinit var binding: ActivityListDataArtikelBinding

    //private val list = ArrayList<DataArtikel>()
    private val listArtikel = ArrayList<DataArtikel>()

    /*fun getListMyDatas(): ArrayList<DataArtikel> {
        val judulArtikel = resources.getStringArray(R.array.judul_artikel)
        val gambarArtikel = resources.getStringArray(R.array.foto_artikel)
        val tanggalPublishArtikel = resources.getStringArray(R.array.tanggal_artikel_dipublish)
        val paragrafSatu = resources.getStringArray(R.array.paragraf_satu)
        val paragrafDua = resources.getStringArray(R.array.paragraf_dua)
        val paragrafTiga = resources.getStringArray(R.array.paragraf_tiga)
        val paragrafEmpat = resources.getStringArray(R.array.paragraf_empat)
        val paragrafLima = resources.getStringArray(R.array.paragraf_lima)
        val listMyData = ArrayList<DataArtikel>()
        for (position in judulArtikel.indices) {
            val myData = DataArtikel(
                judulArtikel[position],
                gambarArtikel[position],
                tanggalPublishArtikel[position],
                paragrafSatu[position],
                paragrafDua[position],
                paragrafTiga[position],
                paragrafEmpat[position],
                paragrafLima[position]
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
    }*/

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                REQUEST_ADD -> if (resultCode == RESULT_ADD) {
                    val quote = data.getParcelableExtra<DataArtikel>(EXTRA_ARTIKEL) as DataArtikel
                    artikelAdapter.addItem(quote)
                    binding.rvDataArtikel.smoothScrollToPosition(artikelAdapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                REQUEST_UPDATE ->
                    when (resultCode) {
                        RESULT_UPDATE -> {
                            val quote = data.getParcelableExtra<DataArtikel>(EXTRA_ARTIKEL) as DataArtikel
                            val position = data.getIntExtra(EXTRA_POSITION, 0)
                            artikelAdapter.updateItem(position, quote)
                            binding.rvDataArtikel.smoothScrollToPosition(position)
                            showSnackbarMessage("Satu item berhasil diubah")
                        }
                        RESULT_DELETE -> {
                            val position = data.getIntExtra(EXTRA_POSITION, 0)
                            artikelAdapter.removeItem(position)
                            showSnackbarMessage("Satu item berhasil dihapus")
                        }
                    }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data_artikel)
        supportActionBar?.hide()
        binding = ActivityListDataArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDataArtikel.layoutManager = LinearLayoutManager(this)
        binding.rvDataArtikel.setHasFixedSize(true)
        artikelAdapter = DataArtikelAdapter(listArtikel, this)

        binding.rvDataArtikel.adapter = artikelAdapter
        artikelHelper = ArtikelHelper.getInstance(applicationContext)

        artikelHelper.open()
        if (savedInstanceState == null) {
            loadQuotes()
        } else {
            val list = savedInstanceState.getParcelableArrayList<DataArtikel>(EXTRA_STATE)
            if (list != null) {
                artikelAdapter.listMyData = list
            }
        }

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
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, artikelAdapter.listMyData)
    }

    private fun loadQuotes() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val cursor = artikelHelper.queryAll()
            var quotes = mapCursorToArrayList(cursor)
            progressbar.visibility = View.INVISIBLE
            if (quotes.size > 0) {
                artikelAdapter.listMyData = quotes
            } else {
                artikelAdapter.listMyData = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvDataArtikel, message, Snackbar.LENGTH_SHORT).show()
    }

}