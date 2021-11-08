package com.esaudev.aristicomp.owner.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.esaudev.aristicomp.databinding.ItemOwnerWalkBinding
import com.esaudev.aristicomp.extensions.load
import com.esaudev.aristicomp.extensions.toDate
import com.esaudev.aristicomp.model.Walk

class OwnerWalkAdapter(
    private val itemClickListener: OnOwnerWalkClickListener
): ListAdapter<Walk, BaseViewHolder<*>>(DiffUtilCallback) {

    interface OnOwnerWalkClickListener{
        fun onOwnerWalkClickListener(walk: Walk)
    }

    private object DiffUtilCallback: DiffUtil.ItemCallback<Walk>() {
        override fun areItemsTheSame(oldItem: Walk, newItem: Walk): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Walk, newItem: Walk): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemOwnerWalkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OwnerWalkViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is OwnerWalkViewHolder -> holder.bind(getItem(position), position)
            else -> {}
        }
    }

    inner class OwnerWalkViewHolder(private val binding: ItemOwnerWalkBinding): BaseViewHolder<Walk>(binding.root) {
        override fun bind(item: Walk, position: Int) = with(binding) {

            sivPet.load(item.petImage)
            tvName.text = item.petName
            tvRace.text = item.petRace
            tvDate.text = item.date
            mcvWalk.setOnClickListener { itemClickListener.onOwnerWalkClickListener(item) }
        }
    }
}