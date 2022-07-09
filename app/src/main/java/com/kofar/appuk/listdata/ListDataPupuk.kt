package com.kofar.appuk.listdata

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kofar.appuk.adapter.DataPupukAdapter
import com.kofar.appuk.data.DataPupuk
import com.kofar.appuk.databinding.ActivityListDataPupukBinding
import com.kofar.appuk.helper.pupukhelper.REQUEST_ADD
import com.kofar.appuk.helper.pupukhelper.REQUEST_UPDATE
import com.kofar.appuk.helper.pupukhelper.RESULT_ADD
import com.kofar.appuk.helper.pupukhelper.RESULT_DELETE
import com.kofar.appuk.helper.pupukhelper.RESULT_UPDATE
import kotlinx.android.synthetic.main.activity_list_data_pupuk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*
class ListDataPupuk : AppCompatActivity() {
    private val list = ArrayList<DataPupuk>()
    private lateinit var pupukAdapter: DataPupukAdapter
    private lateinit var pupuklHelper: ArtikelHelper
    private val EXTRA_STATE = "EXTRA_STATE"
    private lateinit var binding: ActivityListDataPupukBinding

    @SuppressLint("CutPasteId")
    private fun showRecyclerGrid() {
        findViewById<RecyclerView>(R.id.rv_data_pupuk).layoutManager = GridLayoutManager(this, 2)
        val dataPupukAdapter = DataPupukAdapter(list, this)
        findViewById<RecyclerView>(R.id.rv_data_pupuk).adapter = dataPupukAdapter
    }

    private val listPupuk = ArrayList<DataPupuk>()

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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                pupukhelper.REQUEST_ADD -> if (resultCode == pupukhelper.RESULT_ADD) {
                    val quote = data.getParcelableExtra<DataPupuk>(pupukhelper.EXTRA_PUPUK) as DataPupuk
                    pupukAdapter.addItem(quote)
                    binding.rvDataPupuk.smoothScrollToPosition(pupukAdapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                pupukhelper.REQUEST_UPDATE ->
                    when (resultCode) {
                        pupukhelper.RESULT_UPDATE -> {
                            val quote = data.getParcelableExtra<DataPupuk>(pupukhelper.EXTRA_PUPUK) as DataPupuk
                            val position = data.getIntExtra(pupukhelper.EXTRA_POSITION, 0)
                            pupukAdapter.updateItem(position, quote)
                            binding.rvDataPupuk.smoothScrollToPosition(position)
                            showSnackbarMessage("Satu item berhasil diubah")
                        }
                        pupukhelper.RESULT_DELETE -> {
                            val position = data.getIntExtra(pupukhelper.EXTRA_POSITION, 0)
                            pupukAdapter.removeItem(position)
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
        binding = ActivityListDataPupukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDataPupuk.layoutManager = LinearLayoutManager(this)
        binding.rvDataPupuk.setHasFixedSize(true)
        pupukAdapter = DataPupukAdapter(listPupuk, this)

        binding.rvDataPupuk.adapter = pupukAdapter
        pupuklHelper = ArtikelHelper.getInstance(applicationContext)

        pupuklHelper.open()
        if (savedInstanceState == null) {
            loadQuotes()
        } else {
            val list = savedInstanceState.getParcelableArrayList<DataPupuk>(EXTRA_STATE)
            if (list != null) {
                pupukAdapter.listDataPupuks = list
            }
        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, pupukAdapter.listDataPupuks)
    }

    private fun loadQuotes() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val cursor = pupuklHelper.queryAll()
            var quotes = pupukhelper.mapCursorToArrayList(cursor)
            progressbar.visibility = View.INVISIBLE
            if (quotes.size > 0) {
                pupukAdapter.listDataPupuks = quotes
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
}
*/

class ListDataPupuk : AppCompatActivity() {
    private lateinit var adapter: DataPupukAdapter
    private lateinit var binding: ActivityListDataPupukBinding
    private val listPupuk = ArrayList<DataPupuk>()
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityListDataPupukBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""
        firestore = Firebase.firestore
        auth = Firebase.auth
        binding.rvDataPupuk.layoutManager = LinearLayoutManager(this)
        binding.rvDataPupuk.setHasFixedSize(true)
        adapter = DataPupukAdapter(listPupuk, this)
        loadQuotes()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        loadQuotes()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadQuotes() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val listPupuk = ArrayList<DataPupuk>()
            val currentUser = auth.currentUser
            firestore.collection("data_penjualan_produk_pupuk")
                .whereEqualTo("uid", currentUser?.uid)
                .get()
                .addOnSuccessListener { result ->
                    progressbar.visibility = View.INVISIBLE
                    for (document in result) {
                        val id = document.id
                        val nama_produk = document.get("nama_produk").toString()
                        val harga_produk = document.get("harga_produk").toString()
                        val gambar_produk = document.get("gambar_produk").toString()
                        val keterangan_produk = document.get("keterangan_produk").toString()
                        listPupuk.add(DataPupuk(id, nama_produk, harga_produk, gambar_produk, keterangan_produk))
                    }
                    if (listPupuk.size > 0) {
                        binding.rvDataPupuk.adapter = adapter
                        adapter.listDataPupuks = listPupuk
                    } else {
                        adapter.listDataPupuks.clear()
                        binding.rvDataPupuk?.adapter?.notifyDataSetChanged()
                        showSnackbarMessage("Tidak ada data saat ini")
                    }
                }
                .addOnFailureListener { exception ->
                    progressbar.visibility = View.INVISIBLE
                    Toast.makeText(
                        this@ListDataPupuk, "Error adding document",Toast.LENGTH_SHORT
                    ).show()
                }

            val pupukList = ArrayList<DataPupuk>()
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvDataPupuk, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                REQUEST_ADD -> if (resultCode == RESULT_ADD) {
                    loadQuotes()
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                REQUEST_UPDATE ->
                    when (resultCode) {
                        RESULT_UPDATE -> {
                            loadQuotes()
                            showSnackbarMessage("Satu item berhasil diubah")
                        }
                        RESULT_DELETE -> {
                            loadQuotes()
                            showSnackbarMessage("Satu item berhasil dihapus")
                        }
                    }
            }
        }
    }
}