package com.kofar.appuk.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kofar.appuk.R
import com.kofar.appuk.data.DataArtikel
import com.kofar.appuk.detaildata.DetailArtikelActivity

class DataArtikelAdapter(private val activity: Activity) :
    RecyclerView.Adapter<DataArtikelAdapter.ListViewHolder>() {

    var listMyData = ArrayList<DataArtikel>()
    private var artikel: DataArtikel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_artikel, parent, false)
        return ListViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listMyData[position], position)
    }

    fun addItem(artikel: DataArtikel) {
        this.listMyData.add(artikel)
        notifyItemInserted(this.listMyData.size - 1)
    }
    fun updateItem(position: Int, artikel: DataArtikel) {
        this.listMyData[position] = artikel
        notifyItemChanged(position, artikel)
    }
    fun removeItem(position: Int) {
        this.listMyData.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listMyData.size)
    }

    override fun getItemCount(): Int = listMyData.size
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(myData: DataArtikel, position: Int) {
            with(itemView) {
                findViewById<TextView>(R.id.nama_artikel).text = myData.judul_artikel
                findViewById<TextView>(R.id.tanggal_publish_artikel).text = myData.tanggal_publish
                Glide.with(itemView.context)
                    .load(myData.gambar_artikel)
                    .apply(RequestOptions().override(55, 55))
                    .into(findViewById(R.id.gambar_artikel))

                findViewById<CardView>(R.id.artikel_card_view).setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "Kamu Membaca" + listMyData[adapterPosition].judul_artikel,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                findViewById<CardView>(R.id.artikel_card_view).setOnClickListener {
                    val moveWithObjectIntent = Intent(context, DetailArtikelActivity::class.java)
                    activity.overridePendingTransition(0, 0);
                    moveWithObjectIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    activity.overridePendingTransition(0, 0);
                    moveWithObjectIntent.putExtra(DetailArtikelActivity.EXTRA_DATA_ARTIKEL, myData)
                    context.startActivity(moveWithObjectIntent)
                }
            }
        }
    }
}