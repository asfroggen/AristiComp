package com.esaudev.aristicomp.owner.ui.pets.my_pets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentOwnerPetsBinding
import com.esaudev.aristicomp.extensions.showSnackBar
import com.esaudev.aristicomp.extensions.toast
import com.esaudev.aristicomp.model.Pet
import com.esaudev.aristicomp.model.Session
import com.esaudev.aristicomp.owner.ui.adapters.OwnerPetAdapter
import com.esaudev.aristicomp.utils.Constants.PET_BUNDLE
import com.esaudev.aristicomp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerPetsFragment : Fragment(), OwnerPetAdapter.OnOwnerPetClickListener {

    private var _binding: FragmentOwnerPetsBinding? = null
    private val binding: FragmentOwnerPetsBinding
        get() = _binding!!

    private val viewModel: OwnerPetsViewModel by viewModels()
    private var petAdapter: OwnerPetAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentOwnerPetsBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        init()
        initListeners()
    }

    private fun init(){
        initComponents()
        viewModel.getPetsByOwner(Session.USER_LOGGED.id)
    }

    private fun initObservers(){
        viewModel.getPetsState.observe(viewLifecycleOwner, { dataState ->
            when(dataState) {
                is DataState.Loading -> Unit
                is DataState.Success -> handleSuccess(dataState.data)
                is DataState.Error -> handleError()
                else -> Unit
            }
        })
    }

    private fun handleSuccess(pets: List<Pet>){

        petAdapter?.submitList(pets)

        with(binding){
            gEmptyState.visibility = View.GONE
            gSuccessState.visibility = View.VISIBLE
        }
    }

    private fun handleError(){
        with(binding){
            gEmptyState.visibility = View.VISIBLE
            gSuccessState.visibility = View.GONE
        }
    }

    private fun initComponents(){
        petAdapter = OwnerPetAdapter(this)
        binding.rvPets.adapter = petAdapter
        val linearLayoutManager =  LinearLayoutManager(requireContext())
        binding.rvPets.layoutManager = linearLayoutManager
    }

    private fun initListeners(){
        binding.fabNewPet.setOnClickListener {
            findNavController().navigate(R.id.ownerPetsToNewPetFragment)
        }
    }

    override fun onOwnerPetClickListener(pet: Pet) {
        findNavController().navigate(R.id.ownerPetsToUpdatePetFragment, bundleOf(PET_BUNDLE to pet))
    }

}