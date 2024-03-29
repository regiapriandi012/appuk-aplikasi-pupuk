package com.kofar.appuk.adapteredited

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kofar.appuk.addupdate.PupukAddUpdateActivity
import com.kofar.appuk.data.DataPupuk
import com.kofar.appuk.helper.pupukhelper


class DataPupukAdapterEdited(var listDataPupuks: ArrayList<DataPupuk>, val context: Context, val activity: Activity) :
    RecyclerView.Adapter<DataPupukAdapterEdited.GridViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(com.kofar.appuk.R.layout.item_list_pupuk_edited, viewGroup, false)
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

        holder.editPupuk.setOnClickListener{
            val intent = Intent(activity, PupukAddUpdateActivity::class.java)
            activity.overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.overridePendingTransition(0, 0);
            intent.putExtra(pupukhelper.EXTRA_POSITION, position)
            intent.putExtra(pupukhelper.EXTRA_PUPUK, myData)
            activity.startActivityForResult(intent, pupukhelper.REQUEST_UPDATE)
        }
    }

    override fun getItemCount(): Int {
        return listDataPupuks.size
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var editPupuk: Button = itemView.findViewById(com.kofar.appuk.R.id.edit_pupuk)
        var imgPhoto: ImageView = itemView.findViewById(com.kofar.appuk.R.id.gambar_pupuk)
        var namaPupuk: TextView = itemView.findViewById(com.kofar.appuk.R.id.nama_pupuk)
        var hargaPupuk: TextView = itemView.findViewById(com.kofar.appuk.R.id.harga_pupuk)
        var pupukCard: CardView = itemView.findViewById(com.kofar.appuk.R.id.pupuk_card_view)
    }
}
