package com.esaudev.aristicomp.owner.ui.walks.see_walk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentOwnerNewWalkBinding
import com.esaudev.aristicomp.databinding.FragmentOwnerSeeWalkBinding
import com.esaudev.aristicomp.extensions.load
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerSeeWalkFragment : Fragment() {

    private var _binding: FragmentOwnerSeeWalkBinding? = null
    private val binding: FragmentOwnerSeeWalkBinding
        get() = _binding!!

    private lateinit var walk: Walk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            walk = it.getParcelable(Constants.WALK_BUNDLE)?: Walk()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentOwnerSeeWalkBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListeners()
    }

    private fun initView(){
        with(binding){
            ivImage.load(walk.petImage)
            tvOwner.text = walk.ownerName
            tvDuration.text = walk.walkDuration
            tvDate.text = walk.date + " " + walk.time
            tvPetName.text = walk.petName
            tvPetRace.text = walk.petRace
            tvPetAge.text = getString(R.string.walker_walk__age_placeholder, walk.petAge)

            if (walk.comments.isNotEmpty()){
                tvNoComments.visibility = View.GONE
                tvComments.visibility = View.VISIBLE
                tvComments.text = walk.comments
            } else {
                tvComments.visibility = View.GONE
                tvNoComments.visibility = View.VISIBLE
            }

            tvWalker.text = walk.walkerName
        }
    }

    private fun initListeners(){
        with(binding){
            mbBack.setOnClickListener { activity?.onBackPressed() }
        }
    }
}