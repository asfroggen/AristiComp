package com.esaudev.aristicomp.walker.ui.my_walks

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentWalkerSearchBinding
import com.esaudev.aristicomp.databinding.FragmentWalkerWalksBinding
import com.esaudev.aristicomp.extensions.toast
import com.esaudev.aristicomp.model.Session
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.model.WalkStatus
import com.esaudev.aristicomp.utils.Constants.WALK_BUNDLE
import com.esaudev.aristicomp.utils.DataState
import com.esaudev.aristicomp.walker.ui.adapters.WalkerWalkAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalkerWalksFragment : Fragment(), WalkerWalkAdapter.OnWalkerWalkClickListener {

    private var _binding: FragmentWalkerWalksBinding? = null
    private val binding: FragmentWalkerWalksBinding
        get() = _binding!!

    private val viewModel: WalkerWalksViewModel by viewModels()
    private var walksAdapter: WalkerWalkAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentWalkerWalksBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initComponents()
        initDropdown()
    }

    private fun initObservers(){
        viewModel.getWalksByType.observe(viewLifecycleOwner, { dataState ->
            when(dataState){
                is DataState.Success -> handleSuccess(dataState.data)
                is DataState.Error -> handleError()
                is DataState.Loading -> showProgressbar()
                else -> Unit
            }
        })
    }

    private fun showProgressbar(){
        binding.gEmptyState.visibility = View.GONE
        binding.gSuccessState.visibility = View.GONE
        binding.pbWalks.visibility = View.VISIBLE
    }

    private fun hideProgressbar(){
        binding.gEmptyState.visibility = View.GONE
        binding.gSuccessState.visibility = View.GONE
        binding.pbWalks.visibility = View.GONE
    }

    private fun handleSuccess(walks: List<Walk>){
        hideProgressbar()
        walksAdapter?.submitList(walks)
        binding.gEmptyState.visibility = View.GONE
        binding.gSuccessState.visibility = View.VISIBLE
    }

    private fun handleError(){
        hideProgressbar()
        binding.gEmptyState.visibility = View.VISIBLE
        binding.gSuccessState.visibility = View.GONE
    }



    private fun initComponents(){
        walksAdapter = WalkerWalkAdapter(requireContext(), this)
        binding.rvWalks.adapter = walksAdapter
        val linearLayoutManager =  LinearLayoutManager(requireContext())
        binding.rvWalks.layoutManager = linearLayoutManager
    }

    private fun initDropdown(){

        val types: ArrayList<String> = ArrayList()

        types.add(getString(R.string.owner_walks__select_past))
        types.add(getString(R.string.owner_walks__select_accepted))

        val typeAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, types)
        binding.etType.setAdapter(typeAdapter)
        binding.etType.inputType = InputType.TYPE_NULL

        initTextListeners()
    }

    private fun initTextListeners(){
        binding.etType.doOnTextChanged { text, _, _, _ ->
            when(text.toString()){
                getString(R.string.owner_walks__select_past) -> viewModel.getWalksByTypeAndWalker(type = WalkStatus.PAST.toString(),walkerID = Session.USER_LOGGED.id)
                getString(R.string.owner_walks__select_accepted) -> viewModel.getWalksByTypeAndWalker(type = WalkStatus.ACCEPTED.toString(),walkerID = Session.USER_LOGGED.id)
                else -> Unit
            }
        }
    }

    private fun refreshWalks(){
        when(binding.etType.text.toString()){
            getString(R.string.owner_walks__select_past) -> viewModel.getWalksByTypeAndWalker(type = WalkStatus.PAST.toString(),walkerID = Session.USER_LOGGED.id)
            getString(R.string.owner_walks__select_accepted) -> viewModel.getWalksByTypeAndWalker(type = WalkStatus.ACCEPTED.toString(),walkerID = Session.USER_LOGGED.id)
            else -> Unit
        }
    }

    override fun onResume() {
        super.onResume()
        initDropdown()
        refreshWalks()
    }

    override fun onWalkerWalkClickListener(walk: Walk) {
        when(walk.status){
            WalkStatus.PAST.toString() -> findNavController().navigate(R.id.walkerWalksFragmentToWalkerPastFragment, bundleOf(WALK_BUNDLE to walk))
            WalkStatus.ACCEPTED.toString() -> findNavController().navigate(R.id.walkerWalksFragmentToWalkerFinishFragment, bundleOf(WALK_BUNDLE to walk))
        }
    }

}