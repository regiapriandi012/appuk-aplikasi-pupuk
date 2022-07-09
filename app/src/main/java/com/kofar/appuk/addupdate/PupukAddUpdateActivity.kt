package com.kofar.appuk.addupdate

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kofar.appuk.R
import com.kofar.appuk.data.DataPupuk
import com.kofar.appuk.databinding.ActivityPupukAddUpdateBinding
import com.kofar.appuk.helper.pupukhelper.ALERT_DIALOG_CLOSE
import com.kofar.appuk.helper.pupukhelper.EXTRA_POSITION
import com.kofar.appuk.helper.pupukhelper.EXTRA_PUPUK
import com.kofar.appuk.helper.pupukhelper.RESULT_ADD

class PupukAddUpdateActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var isEdit = false
    private var pupuk: DataPupuk? = null
    private var position: Int = 0
    private var categorySelection: Int = 0
    private var categoryName: String = "0"
    private lateinit var binding: com.kofar.appuk.databinding.ActivityPupukAddUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPupukAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pupuk = intent.getParcelableExtra(EXTRA_PUPUK)

        firestore = Firebase.firestore
        auth = Firebase.auth

        if (pupuk != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            pupuk = DataPupuk()
        }
        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            pupuk?.let {
                binding.edtNamaProdukPupuk.setText(it.nama_pupuk)
                binding.edtHargaProdukPupuk.setText(it.harga_pupuk)
                binding.edtGambarProdukPupuk.setText(it.gambar_pupuk)
                binding.edtKeteranganProdukPupuk.setText(it.keterangan_pupuk)
            }!!
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit) {
            val nama_produk = binding.edtNamaProdukPupuk.text.toString().trim()
            val harga_produk = binding.edtHargaProdukPupuk.text.toString().trim()
            val gambar_produk = binding.edtGambarProdukPupuk.text.toString().trim()
            val keterangan_produk = binding.edtKeteranganProdukPupuk.text.toString().trim()
            if (title.isEmpty()) {
                binding.edtNamaProdukPupuk.error = "Field can not be blank"
                return
            }
            if (isEdit) {

            } else {
                val currentUser = auth.currentUser
                val user = hashMapOf(
                    "uid" to currentUser?.uid,
                    "nama_produk" to nama_produk,
                    "harga_produk" to harga_produk,
                    "gambar_produk" to gambar_produk,
                    "keterangan_produk" to keterangan_produk
                )
                firestore.collection("data_penjualan_produk_pupuk")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this@PupukAddUpdateActivity,
                            "DocumentSnapshot added with ID: ${documentReference.id}",
                            Toast.LENGTH_SHORT).show()
                        setResult(RESULT_ADD, intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this@PupukAddUpdateActivity, "Error adding document", Toast.LENGTH_SHORT).show()
                    }

            }
        }
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
        return true
    }*/

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Quote"
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {

                }
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}