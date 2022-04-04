package com.example.plantshandbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.plantshandbook.databinding.PlantItemBinding
import com.example.plantshandbook.entities.ImageItem
import com.example.plantshandbook.utils.Base64CoderDecoder
import com.squareup.picasso.Picasso


class ListImageAdapter(private val listener: Listener) : ListAdapter<ImageItem, ListImageAdapter.ItemHolder>(ItemComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PlantItemBinding.bind(view)

        fun setData(img: ImageItem, listener: Listener) = with(binding) {
            tvTitle.text = img.title
            im.setImageBitmap(Base64CoderDecoder.decoder(img.img))
            //Picasso.get().load(img.path).into(im)
            btnDel.setOnClickListener{

                    listener.deleteItem(img.id!!)


            }
        }
        companion object{
            fun create(parent: ViewGroup): ItemHolder{
                return ItemHolder(
                    LayoutInflater.from(parent.context).
                        inflate(R.layout.plant_item, parent, false))
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

    interface Listener{
        fun deleteItem(id: Int)
        fun onClickItem(note: ImageItem)
    }


}