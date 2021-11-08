package com.esaudev.aristicomp.owner.ui.walks.new_walk

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentOwnerNewWalkBinding
import com.esaudev.aristicomp.databinding.FragmentOwnerWalksBinding
import com.esaudev.aristicomp.extensions.*
import com.esaudev.aristicomp.model.Pet
import com.esaudev.aristicomp.model.Session
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.model.WalkStatus
import com.esaudev.aristicomp.utils.DataState
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_owner.*

@AndroidEntryPoint
class OwnerNewWalkFragment : Fragment() {

    private var _binding: FragmentOwnerNewWalkBinding? = null
    private val binding: FragmentOwnerNewWalkBinding
        get() = _binding!!

    private val viewModel: OwnerNewWalkViewModel by viewModels()

    private var pets: ArrayList<String> = ArrayList()
    private var petList: List<Pet> = listOf()
    private lateinit var petsAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentOwnerNewWalkBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initObservers()
        initListeners()
    }

    private fun init(){
        viewModel.getPetsByOwner(Session.USER_LOGGED.id)
    }

    private fun initObservers(){
        viewModel.saveWalkState.observe(viewLifecycleOwner, { dataState ->
            when(dataState){
                is DataState.Success -> {
                    handleSuccess()
                }
                is DataState.Error -> {
                    handleError()
                }
                is DataState.Loading -> {
                    showProgressBar()
                }
                else -> Unit
            }
        })

        viewModel.getPetsState.observe(viewLifecycleOwner, { dataState ->
            when(dataState){
                is DataState.Success -> {
                    initPets(dataState.data)
                }
                is DataState.Error -> {
                    activity?.toast("Algo salio mal")
                }
                is DataState.Loading -> {
                    petsAreLoading(true)
                }
                else -> Unit
            }
        })
    }

    private fun initListeners(){
        with(binding){
            mbBack.setOnClickListener { activity?.onBackPressed() }
            mbSetDate.setOnClickListener { showDatePickerDialog() }
            mbSetTime.setOnClickListener { showTimePickerDialog() }
            mbSaveWalk.setOnClickListener { handleSaveWalk() }
        }
    }

    private fun handleSaveWalk(){
        if (allDataFilled()){
            viewModel.saveWalk(getWalk())
        } else {
            showSnackBar(getString(R.string.owner_new_walk__select_data))
        }
    }

    private fun getWalk(): Walk {
        val petSelected = getPet(binding.etPet.text.toString())
        return Walk(
            petID = petSelected.id,
            ownerID = Session.USER_LOGGED.id,
            status = WalkStatus.PENDING.toString(),
            date = binding.tvDate.text.toString(),
            time = binding.tvTime.text.toString(),
            ownerName = Session.USER_LOGGED.name,
            comments = binding.etComments.text.toString(),
            petName =petSelected.name,
            petImage = petSelected.image,
            petRace = petSelected.race
        )
    }

    private fun getPet(name: String): Pet{
        return petList[petsAdapter.getPosition(name)]
    }

    private fun allDataFilled(): Boolean {
        return binding.etPet.text.toString() != getString(R.string.owner_new_walk__select_pet_hint) &&
                binding.tvDate.text.toString() != getString(R.string.owner_new_walk__select_date) &&
                binding.tvTime.text.toString() != getString(R.string.owner_new_walk__select_time)
    }

    private fun petsAreLoading(isLoading: Boolean){
        binding.tilSelectPet.isEnabled = !isLoading
    }

    private fun initPets(list: List<Pet>){
        for (i in list){
            pets.add(i.name)
        }
        petList = list
        initDropdown()
        petsAreLoading(false)
    }

    private fun initDropdown(){
        petsAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, pets)
        binding.etPet.setAdapter(petsAdapter)
        binding.etPet.inputType = InputType.TYPE_NULL
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = year.toString() + "-" + (month + 1) + "-" + day.toString()

            binding.tvDate.text = selectedDate.toDate()
        })

        newFragment.show(parentFragmentManager, "datePicker")
    }

    private fun showTimePickerDialog(){

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText(getString(R.string.base_fragment__pick_date_title))
            .build()

        picker.show(childFragmentManager, "timePicker")

        picker.addOnPositiveButtonClickListener{
            val hour = picker.hour
            val minute = picker.minute

            val walkTime = "$hour:$minute"

            binding.tvTime.text = walkTime.toTime()
        }
    }

    private fun showProgressBar(){
        with(binding){
            pbSaveWalk.visibility = View.VISIBLE
            mbSaveWalk.text = ""
            mbSaveWalk.isEnabled = false
        }
    }

    private fun hideProgressBar(){
        with(binding){
            pbSaveWalk.visibility = View.GONE
            mbSaveWalk.text = getString(R.string.owner_new_walk__get_walk_button)
            mbSaveWalk.isEnabled = true
        }
    }

    private fun handleSuccess(){
        hideProgressBar()
        activity?.onBackPressed()
        showSnackBar(getString(R.string.owner_new_walk__publish_success))
    }

    private fun handleError(){
        hideProgressBar()
        showSnackBar(getString(R.string.errors__general_error))
    }

    private fun viewBottomNav(visibility: Boolean){
        if (visibility){
            requireActivity().bnvOwner.visibleFromBottom()
        } else {
            requireActivity().bnvOwner.goneToBottom()
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