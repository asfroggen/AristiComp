package com.esaudev.aristicomp.walker.ui.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.ItemOwnerWalkBinding
import com.esaudev.aristicomp.databinding.ItemWalkerWalkBinding
import com.esaudev.aristicomp.extensions.hasNotPassed
import com.esaudev.aristicomp.extensions.load
import com.esaudev.aristicomp.extensions.toDate
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.model.WalkStatus
import com.esaudev.aristicomp.owner.ui.adapters.BaseViewHolder

class WalkerWalkAdapter(
    private val context: Context,
    private val itemClickListener: OnWalkerWalkClickListener
): ListAdapter<Walk, BaseViewHolder<*>>(DiffUtilCallback) {

    interface OnWalkerWalkClickListener{
        fun onWalkerWalkClickListener(walk: Walk)
    }

    private object DiffUtilCallback: DiffUtil.ItemCallback<Walk>() {
        override fun areItemsTheSame(oldItem: Walk, newItem: Walk): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Walk, newItem: Walk): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemWalkerWalkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WalkerWalkViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is WalkerWalkViewHolder -> holder.bind(getItem(position), position)
            else -> {}
        }
    }

    inner class WalkerWalkViewHolder(private val binding: ItemWalkerWalkBinding): BaseViewHolder<Walk>(binding.root) {
        override fun bind(item: Walk, position: Int) = with(binding) {

            sivPet.load(item.petImage)
            tvName.text = item.petName
            tvRace.text = item.petRace

            if (item.fullDate.hasNotPassed()){
                tvDate.text = item.date

            } else {
                tvDate.setTypeface(null, Typeface.BOLD)
                tvDate.text = context.getString(R.string.walker_search__date_overdue)
            }

            root.setOnClickListener { itemClickListener.onWalkerWalkClickListener(item) }
        }
    }
}