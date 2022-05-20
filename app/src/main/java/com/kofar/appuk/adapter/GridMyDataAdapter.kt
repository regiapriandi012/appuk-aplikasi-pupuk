package com.kofar.appuk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kofar.appuk.MyData


class GridMyDataAdapter(val listMyDatas: ArrayList<MyData>) :
    RecyclerView.Adapter<GridMyDataAdapter.GridViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(com.kofar.appuk.R.layout.item_list, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val myData = listMyDatas[position]
        holder.namaPupuk.text = myData.nama_pupuk
        holder.hargaPupuk.text = myData.harga_pupuk
        Glide.with(holder.itemView.context)
            .load(listMyDatas[position].gambar_pupuk)
            .apply(RequestOptions().override(350, 550))
            .into(holder.imgPhoto)
    }

    override fun getItemCount(): Int {
        return listMyDatas.size
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(com.kofar.appuk.R.id.gambar_pupuk)
        var namaPupuk: TextView = itemView.findViewById(com.kofar.appuk.R.id.nama_pupuk)
        var hargaPupuk: TextView = itemView.findViewById(com.kofar.appuk.R.id.harga_pupuk)
    }
}
