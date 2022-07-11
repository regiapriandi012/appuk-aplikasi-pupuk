package com.kofar.appuk.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kofar.appuk.data.DataPupuk
import com.kofar.appuk.detaildata.DetailPupukActivity


class DataPupukAdapter(var listDataPupuks: ArrayList<DataPupuk>, val context: Context, val activity: Activity) :
    RecyclerView.Adapter<DataPupukAdapter.GridViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(com.kofar.appuk.R.layout.item_list_pupuk, viewGroup, false)
        return GridViewHolder(view)

    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val myData = listDataPupuks[position]
        holder.namaPupuk.text = myData.nama_pupuk
        holder.hargaPupuk.text = myData.harga_pupuk
        Glide.with(holder.itemView.context)
            .load(listDataPupuks[position].gambar_pupuk)
            .apply(RequestOptions().override(350, 550))
            .into(holder.imgPhoto)

        holder.pupukCard.setOnClickListener{ Toast.makeText(holder.itemView.context, "Kamu Memilih " + listDataPupuks[holder.adapterPosition].nama_pupuk, Toast.LENGTH_SHORT).show() }

        holder.pupukCard.setOnClickListener {
            val moveWithObjectIntent = Intent(context, DetailPupukActivity::class.java)
            activity.overridePendingTransition(0, 0);
            moveWithObjectIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.overridePendingTransition(0, 0);
            moveWithObjectIntent.putExtra(DetailPupukActivity.EXTRA_DATA_PUPUK, myData)
            context.startActivity(moveWithObjectIntent)
        }
    }

    override fun getItemCount(): Int {
        return listDataPupuks.size
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(com.kofar.appuk.R.id.gambar_pupuk)
        var namaPupuk: TextView = itemView.findViewById(com.kofar.appuk.R.id.nama_pupuk)
        var hargaPupuk: TextView = itemView.findViewById(com.kofar.appuk.R.id.harga_pupuk)
        var pupukCard: CardView = itemView.findViewById(com.kofar.appuk.R.id.pupuk_card_view)
    }
}
