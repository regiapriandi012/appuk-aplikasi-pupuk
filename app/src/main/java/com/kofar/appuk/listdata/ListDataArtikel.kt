package com.kofar.appuk.listdata

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kofar.appuk.AkunActivity
import com.kofar.appuk.HomeActivity
import com.kofar.appuk.R
import com.kofar.appuk.adapter.DataArtikelAdapter
import com.kofar.appuk.data.DataArtikel
import com.kofar.appuk.databinding.ActivityListDataArtikelBinding
import com.kofar.appuk.db.ArtikelHelper
import com.kofar.appuk.helper.artikelhelper
import com.kofar.appuk.helper.artikelhelper.mapCursorToArrayList
import kotlinx.android.synthetic.main.activity_list_data_artikel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListDataArtikel : AppCompatActivity() {
    private lateinit var artikelHelper: ArtikelHelper
    private lateinit var artikelAdapter: DataArtikelAdapter
    private val EXTRA_STATE = "EXTRA_STATE"
    private lateinit var binding: ActivityListDataArtikelBinding

    private val listArtikel = ArrayList<DataArtikel>()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityListDataArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""
        binding.rvDataArtikel.layoutManager = LinearLayoutManager(this)
        binding.rvDataArtikel.setHasFixedSize(true)
        artikelAdapter = DataArtikelAdapter(this)
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

        findViewById<ImageView>(R.id.tombol_belanja_pupuk_artikel).setOnClickListener {
            val intent = Intent(this, ListDataPupuk::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }

        findViewById<ImageView>(R.id.tombol_akun_artikel).setOnClickListener {
            val intent = Intent(this, AkunActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }

        findViewById<ImageView>(R.id.tombol_kembali_artikel).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }

        findViewById<ImageView>(R.id.tombol_artikel_artikel).setOnClickListener {
            val intent = Intent(this, ListDataArtikel::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }

        findViewById<ImageView>(R.id.tombol_keranjang_belanja_artikel).setOnClickListener {
            val intent = Intent(this, ListDataPupuk::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }

        findViewById<ImageView>(R.id.tombol_home_artikel).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, artikelAdapter.listMyData)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                artikelhelper.REQUEST_ADD -> if (resultCode == artikelhelper.RESULT_ADD) {
                    val quote = data.getParcelableExtra<DataArtikel>(artikelhelper.EXTRA_ARTIKEL) as DataArtikel
                    artikelAdapter.addItem(quote)
                    binding.rvDataArtikel.smoothScrollToPosition(artikelAdapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                artikelhelper.REQUEST_UPDATE ->
                    when (resultCode) {
                        artikelhelper.RESULT_UPDATE -> {
                            val quote = data.getParcelableExtra<DataArtikel>(artikelhelper.EXTRA_ARTIKEL) as DataArtikel
                            val position = data.getIntExtra(artikelhelper.EXTRA_POSITION, 0)
                            artikelAdapter.updateItem(position, quote)
                            binding.rvDataArtikel.smoothScrollToPosition(position)
                            showSnackbarMessage("Satu item berhasil diubah")
                        }
                        artikelhelper.RESULT_DELETE -> {
                            val position = data.getIntExtra(artikelhelper.EXTRA_POSITION, 0)
                            artikelAdapter.removeItem(position)
                            showSnackbarMessage("Satu item berhasil dihapus")
                        }
                    }
            }
        }
    }

    override fun onBackPressed() {
    }
}