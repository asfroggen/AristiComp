package com.esaudev.aristicomp.walker.ui.walk

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentWalkerSearchBinding
import com.esaudev.aristicomp.databinding.FragmentWalkerWalkBinding
import com.esaudev.aristicomp.extensions.goneToBottom
import com.esaudev.aristicomp.extensions.load
import com.esaudev.aristicomp.extensions.showSnackBar
import com.esaudev.aristicomp.extensions.visibleFromBottom
import com.esaudev.aristicomp.model.Pet
import com.esaudev.aristicomp.model.Session
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.model.WalkStatus
import com.esaudev.aristicomp.utils.Constants
import com.esaudev.aristicomp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_owner.*
import kotlinx.android.synthetic.main.activity_walker.*
import kotlinx.android.synthetic.main.fragment_owner_new_walk.*

@AndroidEntryPoint
class WalkerWalkFragment : Fragment() {

    private var _binding: FragmentWalkerWalkBinding? = null
    private val binding: FragmentWalkerWalkBinding
        get() = _binding!!

    private val viewModel: WalkerWalkViewModel by viewModels()

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
        return FragmentWalkerWalkBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        iniObservers()
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
        }
    }

    private fun iniObservers(){
        viewModel.acceptWalksState.observe(viewLifecycleOwner, { dataState ->
            when(dataState){
                is DataState.Success -> handleSuccess()
                is DataState.Error -> handleError()
                is DataState.Loading -> showProgressBar()
                else -> Unit
            }
        })
    }

    private fun showProgressBar(){
        with(binding){
            pbAcceptWalk.visibility = View.VISIBLE
            mbAcceptWalk.text = ""
            mbAcceptWalk.isEnabled = false
        }
    }

    private fun hideProgressBar(){
        with(binding){
            pbAcceptWalk.visibility = View.GONE
            mbAcceptWalk.text = getString(R.string.walker_walk__accept_walk_button)
            mbAcceptWalk.isEnabled = true
        }
    }

    private fun handleSuccess(){
        hideProgressBar()
        activity?.onBackPressed()
        showSnackBar(getString(R.string.walker_walk__walk_accepted))
    }

    private fun handleError(){
        hideProgressBar()
        showSnackBar(getString(R.string.errors__general_error))
    }

    private fun initListeners(){
        with(binding){
            mbBack.setOnClickListener { activity?.onBackPressed() }
            mbAcceptWalk.setOnClickListener { handleAcceptWalk() }
        }
    }

    private fun handleAcceptWalk(){
        viewModel.acceptWalk(getAcceptedWalk())
    }

    private fun getAcceptedWalk(): Walk{
        return Walk(
            id = walk.id,
            petID = walk.petID,
            ownerID = walk.ownerID,
            status = WalkStatus.ACCEPTED.toString(),
            walkerID = Session.USER_LOGGED.id,
            date = walk.date,
            time = walk.time,
            walkDuration = walk.walkDuration,
            ownerName = walk.ownerName,
            walkerName = Session.USER_LOGGED.name,
            comments = walk.comments,
            petImage = walk.petImage,
            petName = walk.petName,
            petRace = walk.petRace,
            fullDate = walk.fullDate,
            petAge = walk.petAge
        )
    }

    private fun viewBottomNav(visibility: Boolean){
        if (visibility){
            requireActivity().bnvWalker.visibleFromBottom()
        } else {
            requireActivity().bnvWalker.goneToBottom()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewBottomNav(false)
    }

    override fun onDetach() {
        super.onDetach()
        viewBottomNav(true)
    }

}