package com.kofar.appuk.adapteredited

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kofar.appuk.R
import com.kofar.appuk.addupdate.ArtikelAddUpdateActivity
import com.kofar.appuk.data.DataArtikel
import com.kofar.appuk.helper.artikelhelper
import com.kofar.appuk.helper.artikelhelper.EXTRA_ARTIKEL
import com.kofar.appuk.helper.artikelhelper.EXTRA_POSITION

class DataArtikelAdapterEdited(var listMyData: ArrayList<DataArtikel>, private val activity: Activity) :
    RecyclerView.Adapter<DataArtikelAdapterEdited.ListViewHolder>() {

    private var artikel: DataArtikel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_artikel_edited, parent, false)
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
            with(itemView){
                findViewById<TextView>(R.id.nama_artikel).text = myData.judul_artikel
                findViewById<TextView>(R.id.tanggal_publish_artikel).text = myData.tanggal_publish
                Glide.with(itemView.context)
                    .load(myData.gambar_artikel)
                    .apply(RequestOptions().override(55, 55))
                    .into(findViewById(R.id.gambar_artikel))

                findViewById<Button>(R.id.edit_artikel).setOnClickListener{
                    val intent = Intent(activity, ArtikelAddUpdateActivity::class.java)
                    activity.overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    activity.overridePendingTransition(0, 0);
                    intent.putExtra(EXTRA_POSITION, position)
                    intent.putExtra(EXTRA_ARTIKEL, myData)
                    activity.startActivityForResult(intent, artikelhelper.REQUEST_UPDATE)
                }
            }
        }
    }
}