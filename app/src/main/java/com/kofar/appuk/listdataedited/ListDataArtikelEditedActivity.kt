package com.kofar.appuk.listdataedited

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kofar.appuk.AkunActivity
import com.kofar.appuk.R
import com.kofar.appuk.adapteredited.DataArtikelAdapterEdited
import com.kofar.appuk.data.DataArtikel
import com.kofar.appuk.databinding.ActivityListDataArtikelEditedBinding
import com.kofar.appuk.db.ArtikelHelper
import com.kofar.appuk.helper.artikelhelper.EXTRA_ARTIKEL
import com.kofar.appuk.helper.artikelhelper.EXTRA_POSITION
import com.kofar.appuk.helper.artikelhelper.REQUEST_ADD
import com.kofar.appuk.helper.artikelhelper.REQUEST_UPDATE
import com.kofar.appuk.helper.artikelhelper.RESULT_ADD
import com.kofar.appuk.helper.artikelhelper.RESULT_DELETE
import com.kofar.appuk.helper.artikelhelper.RESULT_UPDATE
import com.kofar.appuk.helper.artikelhelper.mapCursorToArrayList
import kotlinx.android.synthetic.main.activity_list_data_artikel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListDataArtikelEditedActivity : AppCompatActivity() {
    private lateinit var artikelHelper: ArtikelHelper
    private lateinit var artikelAdapter: DataArtikelAdapterEdited
    private val EXTRA_STATE = "EXTRA_STATE"
    private lateinit var binding: ActivityListDataArtikelEditedBinding

    private val listArtikel = ArrayList<DataArtikel>()

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
        binding = ActivityListDataArtikelEditedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDataArtikel.layoutManager = LinearLayoutManager(this)
        binding.rvDataArtikel.setHasFixedSize(true)
        artikelAdapter = DataArtikelAdapterEdited(listArtikel, this)

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

        findViewById<Button>(R.id.tombol_kembali_artikel).setOnClickListener {
            val intent = Intent(this, AkunActivity::class.java)
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