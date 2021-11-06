package com.esaudev.aristicomp.owner.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.esaudev.aristicomp.databinding.ItemPetBinding
import com.esaudev.aristicomp.extensions.load
import com.esaudev.aristicomp.model.Pet

class OwnerPetAdapter(
    private val itemClickListener: OnOwnerPetClickListener
): ListAdapter<Pet, BaseViewHolder<*>>(DiffUtilCallback) {

    interface OnOwnerPetClickListener{
        fun onOwnerPetClickListener(pet: Pet)
    }

    private object DiffUtilCallback: DiffUtil.ItemCallback<Pet>() {
        override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemPetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OwnerPetViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is OwnerPetViewHolder -> holder.bind(getItem(position), position)
            else -> {}
        }
    }

    inner class OwnerPetViewHolder(private val binding: ItemPetBinding): BaseViewHolder<Pet>(binding.root) {
        override fun bind(item: Pet, position: Int) = with(binding) {

            ivImage.load(item.image)
            tvName.text = item.name
            mcvPet.setOnClickListener { itemClickListener.onOwnerPetClickListener(item) }
        }
    }
}