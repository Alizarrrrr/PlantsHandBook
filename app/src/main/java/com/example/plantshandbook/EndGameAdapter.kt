package com.example.plantshandbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.plantshandbook.databinding.EndGameItemBinding
import com.example.plantshandbook.databinding.ImageItemBinding
import com.example.plantshandbook.entities.EndGameItem

import com.example.plantshandbook.utils.Base64CoderDecoder


class EndGameAdapter() : ListAdapter<EndGameItem, EndGameAdapter.ItemHolder>(ItemComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position))
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = EndGameItemBinding.bind(view)

        fun setData(img: EndGameItem) = with(binding) {
            var num = img.number+1
            tvNumberItem.text = (num).toString()  //номер изображения
            imItem.setImageBitmap(Base64CoderDecoder.decoder(img.img))
            tvSelect.text = img.select   //выбранное имя
            tvRight.text = img.right    //правильное имя
            when(img.result){
                true -> {
                    tvNumberItem.setBackgroundResource(R.drawable.round_text_view_green)
                }
                false -> {
                    tvNumberItem.setBackgroundResource(R.drawable.round_text_view_red)
                }
            }


        }
        companion object{
            fun create(parent: ViewGroup): ItemHolder{
                return ItemHolder(
                    LayoutInflater.from(parent.context).
                        inflate(R.layout.end_game_item, parent, false))
            }
        }
    }
    class ItemComparator : DiffUtil.ItemCallback<EndGameItem>(){
        override fun areItemsTheSame(oldItem: EndGameItem, newItem: EndGameItem): Boolean {
            return oldItem.number ==newItem.number
        }

        override fun areContentsTheSame(oldItem: EndGameItem, newItem: EndGameItem): Boolean {
            return oldItem == newItem
        }

    }

}