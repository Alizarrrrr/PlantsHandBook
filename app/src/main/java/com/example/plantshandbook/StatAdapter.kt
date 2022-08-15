package com.example.plantshandbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.plantshandbook.databinding.StatItemBinding
import com.example.plantshandbook.entities.ImageItem
import com.example.plantshandbook.utils.Base64CoderDecoder
import androidx.recyclerview.widget.DiffUtil
import kotlin.math.floor


class StatAdapter() : ListAdapter<ImageItem, StatAdapter.ItemHolder>(ItemComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position))
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = StatItemBinding.bind(view)

        fun setData(img: ImageItem) = with(binding) {
            tvSelect.text = img.title
            imItem.setImageBitmap(Base64CoderDecoder.decoder(img.img))
            val progressBarValAll = img.true_count_free.toString()+"/"+img.all_count_free.toString()
            val progressBarSetAll = floor(img.true_count_free.toFloat()/img.all_count_free.toFloat()*100).toInt()
            val progressBarVal10g = img.true_count_10g.toString()+"/"+img.all_count_10g.toString()
            val progressBarSet10g = floor(img.true_count_10g.toFloat()/img.all_count_10g.toFloat()*100).toInt()
            //tvRightAllHeader.text = progressBarValAll
            //tvRight10GHeader.text = progressBarVal10g
            pbHorizontalAll.progress = progressBarSetAll
            tvProgressbarAll.text = progressBarSetAll.toString()+"% "+progressBarValAll
            pbHorizontal10G.progress = progressBarSet10g
            tvProgressBar10G.text = progressBarSet10g.toString()+"% "+progressBarVal10g

        }
        companion object{
            fun create(parent: ViewGroup): ItemHolder{
                return ItemHolder(
                    LayoutInflater.from(parent.context).
                    inflate(R.layout.stat_item, parent, false))
            }
        }
    }
    class ItemComparator : DiffUtil.ItemCallback<ImageItem>(){
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.id ==newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }

    }




}