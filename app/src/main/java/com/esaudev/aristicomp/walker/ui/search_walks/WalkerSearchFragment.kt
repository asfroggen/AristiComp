package com.esaudev.aristicomp.walker.ui.search_walks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentOwnerNewWalkBinding
import com.esaudev.aristicomp.databinding.FragmentWalkerSearchBinding
import com.esaudev.aristicomp.extensions.hasNotPassed
import com.esaudev.aristicomp.extensions.showSnackBar
import com.esaudev.aristicomp.extensions.toast
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.owner.ui.adapters.OwnerWalkAdapter
import com.esaudev.aristicomp.utils.Constants.WALK_BUNDLE
import com.esaudev.aristicomp.utils.DataState
import com.esaudev.aristicomp.walker.ui.adapters.WalkerWalkAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalkerSearchFragment : Fragment(), WalkerWalkAdapter.OnWalkerWalkClickListener {

    private var _binding: FragmentWalkerSearchBinding? = null
    private val binding: FragmentWalkerSearchBinding
        get() = _binding!!

    private val viewModel: WalkerSearchViewModel by viewModels()
    private var walksAdapter: WalkerWalkAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentWalkerSearchBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        initObservers()
        init()
    }

    private fun initComponents(){
        walksAdapter = WalkerWalkAdapter(requireContext(), this)
        binding.rvWalks.adapter = walksAdapter
        val linearLayoutManager =  LinearLayoutManager(requireContext())
        binding.rvWalks.layoutManager = linearLayoutManager
    }

    private fun initObservers(){
        viewModel.getWalksAvailable.observe(viewLifecycleOwner, { dataState ->
            when(dataState){
                is DataState.Success -> handleSuccess(dataState.data)
                is DataState.Loading -> showProgressBar()
                is DataState.Error -> handleError()
                else -> Unit
            }
        })
    }

    private fun handleSuccess(walks: List<Walk>){
        hideProgressbar()
        binding.gEmptyState.visibility = View.GONE
        binding.gSuccessState.visibility = View.VISIBLE
        walksAdapter?.submitList(walks)
    }

    private fun handleError(){
        hideProgressbar()
        binding.gEmptyState.visibility = View.VISIBLE
        binding.gSuccessState.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.gEmptyState.visibility = View.GONE
        binding.gSuccessState.visibility = View.GONE
        binding.pbWalks.visibility = View.VISIBLE
    }

    private fun hideProgressbar(){
        binding.gEmptyState.visibility = View.GONE
        binding.gSuccessState.visibility = View.GONE
        binding.pbWalks.visibility = View.GONE
    }


    private fun init(){
        viewModel.getWalksAvailable()
    }

    override fun onWalkerWalkClickListener(walk: Walk) {
        if (walk.fullDate.hasNotPassed()){
            findNavController().navigate(R.id.walkerSearchToWalkerWalkFragment, bundleOf(WALK_BUNDLE to walk))
        } else {
            showSnackBar(getString(R.string.walker_search__date_overdue_disclaimer))
        }

    }


}