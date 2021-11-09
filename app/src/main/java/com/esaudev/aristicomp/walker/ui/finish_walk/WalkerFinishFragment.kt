package com.esaudev.aristicomp.walker.ui.finish_walk

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentWalkerFinishBinding
import com.esaudev.aristicomp.extensions.goneToBottom
import com.esaudev.aristicomp.extensions.load
import com.esaudev.aristicomp.extensions.showSnackBar
import com.esaudev.aristicomp.extensions.visibleFromBottom
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.model.WalkStatus
import com.esaudev.aristicomp.utils.Constants
import com.esaudev.aristicomp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_walker.*

@AndroidEntryPoint
class WalkerFinishFragment : Fragment() {

    private var _binding: FragmentWalkerFinishBinding? = null
    private val binding: FragmentWalkerFinishBinding
        get() = _binding!!

    private val viewModel: WalkerFinishViewModel by viewModels()
    private lateinit var walk: Walk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            walk = it.getParcelable(Constants.WALK_BUNDLE)?: Walk()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentWalkerFinishBinding
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
        viewModel.finishWalkState.observe(viewLifecycleOwner, { dataState ->
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
            pbFinishWalk.visibility = View.VISIBLE
            mbFinishWalk.text = ""
            mbFinishWalk.isEnabled = false
        }
    }

    private fun hideProgressBar(){
        with(binding){
            pbFinishWalk.visibility = View.GONE
            mbFinishWalk.text = getString(R.string.walker_walk__finish_walk_button)
            mbFinishWalk.isEnabled = true
        }
    }

    private fun handleSuccess(){
        hideProgressBar()
        activity?.onBackPressed()
        showSnackBar(getString(R.string.walker_walk__walk_finish))
    }

    private fun handleError(){
        hideProgressBar()
        showSnackBar(getString(R.string.errors__general_error))
    }

    private fun initListeners(){
        with(binding){
            mbBack.setOnClickListener { activity?.onBackPressed() }
            mbFinishWalk.setOnClickListener { handleFinishWalk() }
        }
    }

    private fun handleFinishWalk(){
        viewModel.finishWalk(getFinishedWalk())
    }

    private fun getFinishedWalk(): Walk {
        return walk.copy(
            status = WalkStatus.PAST.toString()
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