package com.kofar.appuk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kofar.appuk.MyData
import com.kofar.appuk.R
import de.hdodenhof.circleimageview.CircleImageView

class ListMyDataAdapter(private val listMyData: ArrayList<MyData>) :
    RecyclerView.Adapter<ListMyDataAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listMyData[position])
    }
    override fun getItemCount(): Int = listMyData.size
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(myData: MyData) {
            with(itemView){
                Glide.with(itemView.context)
                    .load(myData.gambar_pupuk)
                    .apply(RequestOptions().override(350, 550))
                    .into(findViewById<CircleImageView>(R.id.gambar_pupuk))
                //findViewById<TextView>(R.id.nama_pupuk).text = myData.nama_pupuk
                //findViewById<TextView>(R.id.harga_pupuk).text = myData.harga_pupuk.toString()
            }
        }
    }
}