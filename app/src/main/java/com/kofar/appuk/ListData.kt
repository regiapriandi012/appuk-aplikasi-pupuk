package com.kofar.appuk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kofar.appuk.adapter.GridMyDataAdapter

class ListData : AppCompatActivity() {
    private val list = ArrayList<MyData>()

    private fun showRecyclerGrid() {
        findViewById<RecyclerView>(R.id.rv_mydata).layoutManager = GridLayoutManager(this, 2)
        val gridMyDataAdapter = GridMyDataAdapter(list)
        findViewById<RecyclerView>(R.id.rv_mydata).adapter = gridMyDataAdapter
    }

    fun getListMyDatas(): ArrayList<MyData> {
        val dataNamaPupuk = resources.getStringArray(R.array.data_nama_pupuk)
        val dataHargaPupuk = resources.getStringArray(R.array.data_harga_pupuk)
        val dataGambarPupuk = resources.getStringArray(R.array.data_gambar_pupuk)
        val listMyData = ArrayList<MyData>()
        for (position in dataNamaPupuk.indices) {
            val myData = MyData(
                dataNamaPupuk[position],
                dataHargaPupuk[position],
                dataGambarPupuk[position]
                //dataDescription[position],
                //dataPhoto[position]
            )
            listMyData.add(myData)
        }
        return listMyData
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data)
        supportActionBar?.hide()

        findViewById<RecyclerView>(R.id.rv_mydata).setHasFixedSize(true)
        list.addAll(getListMyDatas())
        showRecyclerGrid()
    }

}