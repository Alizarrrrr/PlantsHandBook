package com.example.plantshandbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.example.plantshandbook.databinding.PlantItemBinding
import java.util.ArrayList
import kotlinx.android.synthetic.main.plant_item.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.plant_item.view.*

class PlantAdapter internal constructor() :
    RecyclerView.Adapter<PlantAdapter.ViewHolderRecyclerView>() {
    val plantList = ArrayList<Plant>()
    var plantName: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRecyclerView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plant_item, parent, false)
        return ViewHolderRecyclerView(view)
    }

    override fun onBindViewHolder(holder: ViewHolderRecyclerView, position: Int) {
        val currentItem = plantList[position]
        holder.im.setImageResource(currentItem.imageId)
        holder.tvTitle.text = currentItem.title + " ${currentItem.position}"
        holder.index = position

        holder.btnDel.setOnClickListener {
            plantList.removeAt(holder.index)
            checkPosition()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

//    fun checkCountPlant(name:String): Int{
//        var count = 0
//        for (plant in plantList){
//            if (plant.title == name) {
//                count++
//            }
//        }
//        return count
//    }

    fun checkPosition() {
        var index_0 = 0
        var index_1 = 0
        var index_2 = 0
        var index_3 = 0
        var index_4 = 0

        for (plant in plantList) {
            when (plant.title) {
                plantName[0] -> {
                    plant.position = index_0
                    index_0++
                }
                plantName[1] -> {
                    plant.position = index_1
                    index_1++

                }
                plantName[2] -> {
                    plant.position = index_2
                    index_2++

                }
                plantName[3] -> {
                    plant.position = index_3
                    index_3++

                }
                plantName[4] -> {
                    plant.position = index_4
                    index_4++

                }
            }
        }
    }

    fun setPlantNames(list: Array<String>) {
        if (plantName.size == 0) {
            for (item in list) {
                plantName.add(item)
            }
        }
    }


    fun addPlant(plant: Plant) {
        plantList.add(plant)
        checkPosition()
        notifyDataSetChanged()
    }


    inner class ViewHolderRecyclerView(item: View) : RecyclerView.ViewHolder(item) {
        val im: ImageView = item.im
        val tvTitle: TextView = item.tvTitle
        val btnDel: Button = item.btn_del
        var index = 0
    }
}