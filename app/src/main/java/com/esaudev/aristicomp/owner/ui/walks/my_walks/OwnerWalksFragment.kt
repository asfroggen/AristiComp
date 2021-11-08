package com.esaudev.aristicomp.owner.ui.walks.my_walks

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentOwnerWalksBinding
import com.esaudev.aristicomp.extensions.showSnackBar
import com.esaudev.aristicomp.extensions.toast
import com.esaudev.aristicomp.model.Session
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.model.WalkStatus
import com.esaudev.aristicomp.owner.ui.adapters.OwnerPetAdapter
import com.esaudev.aristicomp.owner.ui.adapters.OwnerWalkAdapter
import com.esaudev.aristicomp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerWalksFragment : Fragment(), OwnerWalkAdapter.OnOwnerWalkClickListener {

    private var _binding: FragmentOwnerWalksBinding? = null
    private val binding: FragmentOwnerWalksBinding
        get() = _binding!!

    private val viewModel: OwnerWalksViewModel by viewModels()
    private var walksAdapter: OwnerWalkAdapter? = null
    private var walkList: List<Walk> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentOwnerWalksBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        initDropdown()
        initObservers()
        initWalks()
        initListeners()
    }

    private fun initComponents(){
        walksAdapter = OwnerWalkAdapter(this)
        binding.rvWalks.adapter = walksAdapter
        val linearLayoutManager =  LinearLayoutManager(requireContext())
        binding.rvWalks.layoutManager = linearLayoutManager
    }

    private fun initDropdown(){

        val types: ArrayList<String> = ArrayList()

        types.add(getString(R.string.owner_walks__select_active))
        types.add(getString(R.string.owner_walks__select_past))
        types.add(getString(R.string.owner_walks__select_accepted))

        val typeAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, types)
        binding.etType.setAdapter(typeAdapter)
        binding.etType.inputType = InputType.TYPE_NULL

        initTextListeners()
    }

    private fun initObservers(){
        viewModel.getWalksByType.observe(viewLifecycleOwner, { dataState ->
            Log.d("TAG_ESAU", dataState.toString())
            when(dataState){
                is DataState.Loading -> Unit
                is DataState.Success -> handleSuccess(dataState.data)
                is DataState.Error -> handleError()
                else -> Unit
            }
        })
    }

    private fun initWalks(){
        viewModel.getWalksByType(ownerID = Session.USER_LOGGED.id)
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
        binding.gEmptyState.visibility = View.GONE
        binding.gSuccessState.visibility = View.VISIBLE
        walkList = walks
        walksAdapter?.submitList(walkList.filter { it.status == WalkStatus.PENDING.toString() })
    }

    private fun handleError(){
        binding.gEmptyState.visibility = View.VISIBLE
        binding.gSuccessState.visibility = View.GONE
    }

    private fun initTextListeners(){
        binding.etType.doOnTextChanged { text, _, _, _ ->
            when(text.toString()){
                getString(R.string.owner_walks__select_active) -> walksAdapter?.submitList(walkList.filter { it.status == WalkStatus.PENDING.toString() })
                getString(R.string.owner_walks__select_past) -> walksAdapter?.submitList(walkList.filter { it.status == WalkStatus.PAST.toString() })
                getString(R.string.owner_walks__select_accepted) -> walksAdapter?.submitList(walkList.filter { it.status == WalkStatus.ACCEPTED.toString() })
                else -> Unit
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initDropdown()
    }

    private fun initListeners(){
        with(binding){
            fabNewWalk.setOnClickListener {
                findNavController().navigate(R.id.ownerWalksToNewWalkFragment)
            }
        }
    }

    override fun onOwnerWalkClickListener(pet: Walk) {
        activity?.toast("Clicked")
    }

}