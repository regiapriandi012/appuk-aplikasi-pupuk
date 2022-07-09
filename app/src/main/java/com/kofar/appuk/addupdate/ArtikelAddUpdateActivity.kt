package com.kofar.appuk.addupdate

import android.app.ActionBar
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.kofar.appuk.R
import com.kofar.appuk.data.DataArtikel
import com.kofar.appuk.databinding.ActivityArtikelAddUpdateBinding
import com.kofar.appuk.db.ArtikelHelper
import com.kofar.appuk.db.DatabaseArtikelContract
import com.kofar.appuk.db.DatabaseArtikelContract.QuoteColumns.Companion.tanggal_publish
import com.kofar.appuk.helper.artikelhelper.ALERT_DIALOG_CLOSE
import com.kofar.appuk.helper.artikelhelper.ALERT_DIALOG_DELETE
import com.kofar.appuk.helper.artikelhelper.EXTRA_ARTIKEL
import com.kofar.appuk.helper.artikelhelper.EXTRA_POSITION
import com.kofar.appuk.helper.artikelhelper.RESULT_ADD
import com.kofar.appuk.helper.artikelhelper.RESULT_DELETE
import com.kofar.appuk.helper.artikelhelper.RESULT_UPDATE
import com.kofar.appuk.helper.artikelhelper.getCurrentDate


class ArtikelAddUpdateActivity : AppCompatActivity(), View.OnClickListener {
    private var isEdit = false
    private var artikel: DataArtikel? = null
    private var position: Int = 0
    private lateinit var artikelHelper: ArtikelHelper
    private lateinit var binding: ActivityArtikelAddUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        supportActionBar?.title = ""
        val bar: ActionBar? = actionBar
        bar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        binding = ActivityArtikelAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        artikelHelper = ArtikelHelper.getInstance(applicationContext)
        artikelHelper.open()
        artikel = intent.getParcelableExtra(EXTRA_ARTIKEL)
        if (artikel != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            artikel = DataArtikel()
        }
        val actionBarTitle: String
        val btnTitle: String
        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            artikel?.let {
                binding.edtJudulArtikel.setText(it.judul_artikel)
                binding.edtGambarArtikel.setText(it.gambar_artikel)
                binding.edtParagrafSatu.setText(it.paragraf_satu)
                binding.edtParagrafDua.setText(it.paragraf_dua)
                binding.edtParagrafTiga.setText(it.paragraf_tiga)
                binding.edtParagrafEmpat.setText(it.paragraf_empat)
                binding.edtParagrafLima.setText(it.paragraf_lima)
            }!!
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit) {
            val judul_artikel = binding.edtJudulArtikel.text.toString().trim()
            val gambar_artikel = binding.edtGambarArtikel.text.toString().trim()
            val paragraf_satu = binding.edtParagrafSatu.text.toString().trim()
            val paragraf_dua = binding.edtParagrafTiga.text.toString().trim()
            val paragraf_tiga = binding.edtParagrafTiga.text.toString().trim()
            val paragraf_empat = binding.edtParagrafEmpat.text.toString().trim()
            val paragraf_lima = binding.edtParagrafLima.text.toString().trim()
            if (judul_artikel.isEmpty()) {
                binding.edtJudulArtikel.error = "Field can not be blank"
                return
            }
            if (gambar_artikel.isEmpty()) {
                binding.edtGambarArtikel.error = "Field can not be blank"
                return
            }
            if (paragraf_satu.isEmpty()) {
                binding.edtParagrafSatu.error = "Field can not be blank"
                return
            }
            if (paragraf_dua.isEmpty()) {
                binding.edtParagrafDua.error = "Field can not be blank"
                return
            }
            if (paragraf_tiga.isEmpty()) {
                binding.edtParagrafTiga.error = "Field can not be blank"
                return
            }
            if (paragraf_empat.isEmpty()) {
                binding.edtParagrafEmpat.error = "Field can not be blank"
                return
            }
            if (paragraf_lima.isEmpty()) {
                binding.edtParagrafLima.error = "Field can not be blank"
                return
            }
            artikel?.judul_artikel = judul_artikel
            artikel?.gambar_artikel = gambar_artikel
            artikel?.paragraf_satu = paragraf_satu
            artikel?.paragraf_dua = paragraf_dua
            artikel?.paragraf_tiga = paragraf_tiga
            artikel?.paragraf_empat = paragraf_empat
            artikel?.paragraf_lima = paragraf_lima

            val intent = Intent()
            intent.putExtra(EXTRA_ARTIKEL, artikel)
            intent.putExtra(EXTRA_POSITION, position)
            val values = ContentValues()
            values.put(DatabaseArtikelContract.QuoteColumns.judul_artikel, judul_artikel)
            values.put(DatabaseArtikelContract.QuoteColumns.gambar_artikel, gambar_artikel)
            values.put(DatabaseArtikelContract.QuoteColumns.paragraf_satu, paragraf_satu)
            values.put(DatabaseArtikelContract.QuoteColumns.paragraf_dua, paragraf_dua)
            values.put(DatabaseArtikelContract.QuoteColumns.paragraf_tiga, paragraf_tiga)
            values.put(DatabaseArtikelContract.QuoteColumns.paragraf_empat, paragraf_empat)
            values.put(DatabaseArtikelContract.QuoteColumns.paragraf_lima, paragraf_lima)
            if (isEdit) {
                val result = artikelHelper.update(artikel?.id.toString(),
                    values).toLong()
                if (result > 0) {
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    Toast.makeText(this@ArtikelAddUpdateActivity, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
                }
            } else {
                artikel?.tanggal_publish = getCurrentDate()
                val acct = GoogleSignIn.getLastSignedInAccount(this)
                val personName = acct?.displayName
                artikel?.penulis_artikel = personName
                values.put(DatabaseArtikelContract.QuoteColumns.penulis_artikel, personName)
                values.put(tanggal_publish, getCurrentDate())
                val result = artikelHelper.insert(values)
                if (result > 0) {
                    artikel?.id = result.toInt()
                    setResult(RESULT_ADD, intent)
                    finish()
                } else {
                    Toast.makeText(this@ArtikelAddUpdateActivity, "Gagal menambah data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Artikel"
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
                    val result = artikelHelper.deleteById(artikel?.id.toString()).toLong()
                    if (result > 0) {
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, position)
                        setResult(RESULT_DELETE, intent)
                        finish()
                    } else {
                        Toast.makeText(this@ArtikelAddUpdateActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}