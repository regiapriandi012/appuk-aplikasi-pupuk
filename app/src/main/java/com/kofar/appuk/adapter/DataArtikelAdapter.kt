package com.kofar.appuk.adapter

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
import com.kofar.appuk.DataArtikel
import com.kofar.appuk.DetailArtikelActivity
import com.kofar.appuk.R

class DataArtikelAdapter(private val listMyData: ArrayList<DataArtikel>) :
    RecyclerView.Adapter<DataArtikelAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_artikel, parent, false)
        return ListViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listMyData[position])
    }
    override fun getItemCount(): Int = listMyData.size
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(myData: DataArtikel) {
            with(itemView){
                findViewById<TextView>(R.id.nama_artikel).text = myData.judul_artikel
                findViewById<TextView>(R.id.tanggal_publish_artikel).text = myData.tanggal_publish
                Glide.with(itemView.context)
                    .load(myData.gambar_artikel)
                    .apply(RequestOptions().override(55, 55))
                    .into(findViewById(R.id.gambar_artikel))

                findViewById<CardView>(R.id.artikel_card_view).setOnClickListener{ Toast.makeText(itemView.context, "Kamu Membaca" + listMyData[adapterPosition].judul_artikel, Toast.LENGTH_SHORT).show() }

                findViewById<CardView>(R.id.artikel_card_view).setOnClickListener {
                    val moveWithObjectIntent = Intent(context, DetailArtikelActivity::class.java)
                    moveWithObjectIntent.putExtra(DetailArtikelActivity.EXTRA_DATA_ARTIKEL, myData)
                    context.startActivity(moveWithObjectIntent)
                }
            }
        }
    }
}