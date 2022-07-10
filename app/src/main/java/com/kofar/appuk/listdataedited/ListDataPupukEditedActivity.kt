package com.kofar.appuk.listdataedited

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kofar.appuk.AkunActivity
import com.kofar.appuk.R
import com.kofar.appuk.adapteredited.DataPupukAdapterEdited
import com.kofar.appuk.data.DataPupuk
import com.kofar.appuk.databinding.ActivityListDataPupukEditedBinding
import com.kofar.appuk.helper.pupukhelper.REQUEST_ADD
import com.kofar.appuk.helper.pupukhelper.REQUEST_UPDATE
import com.kofar.appuk.helper.pupukhelper.RESULT_ADD
import com.kofar.appuk.helper.pupukhelper.RESULT_DELETE
import com.kofar.appuk.helper.pupukhelper.RESULT_UPDATE
import kotlinx.android.synthetic.main.activity_list_data_pupuk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListDataPupukEditedActivity : AppCompatActivity() {
    private lateinit var adapter: DataPupukAdapterEdited
    private lateinit var binding: ActivityListDataPupukEditedBinding
    private val listPupuk = ArrayList<DataPupuk>()
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityListDataPupukEditedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""
        firestore = Firebase.firestore
        auth = Firebase.auth
        binding.rvDataPupuk.layoutManager = GridLayoutManager(this, 2)
        binding.rvDataPupuk.setHasFixedSize(true)
        adapter = DataPupukAdapterEdited(listPupuk, this, this)
        loadQuotes()

        findViewById<Button>(R.id.tombol_kembali_pupuk).setOnClickListener {
            val intent = Intent(this, AkunActivity::class.java)
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }
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
                        this@ListDataPupukEditedActivity, "Error adding document",Toast.LENGTH_SHORT
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