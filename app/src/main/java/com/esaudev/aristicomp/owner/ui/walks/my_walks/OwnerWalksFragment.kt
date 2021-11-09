package com.esaudev.aristicomp.owner.ui.walks.my_walks

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.auth.ui.LoginActivity
import com.esaudev.aristicomp.databinding.FragmentOwnerWalksBinding
import com.esaudev.aristicomp.extensions.showSnackBar
import com.esaudev.aristicomp.extensions.toast
import com.esaudev.aristicomp.model.Session
import com.esaudev.aristicomp.model.Walk
import com.esaudev.aristicomp.model.WalkStatus
import com.esaudev.aristicomp.owner.ui.adapters.OwnerWalkAdapter
import com.esaudev.aristicomp.utils.Constants.WALK_BUNDLE
import com.esaudev.aristicomp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_owner_walks.*
import javax.inject.Inject

@AndroidEntryPoint
class OwnerWalksFragment : Fragment(), OwnerWalkAdapter.OnOwnerWalkClickListener {

    private var _binding: FragmentOwnerWalksBinding? = null
    private val binding: FragmentOwnerWalksBinding
        get() = _binding!!

    private val viewModel: OwnerWalksViewModel by viewModels()
    private var walksAdapter: OwnerWalkAdapter? = null

    @Inject
    lateinit var sharedPrefs: SharedPreferences

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
        initListeners()
    }

    private fun initComponents(){
        walksAdapter = OwnerWalkAdapter(requireContext(), this)
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
            when(dataState){
                is DataState.Loading -> showProgressbar()
                is DataState.Success -> handleWalksSuccess(dataState.data)
                is DataState.Error -> handleWalksError()
                else -> Unit
            }
        })

        viewModel.deleteWalkState.observe(viewLifecycleOwner, { dataState ->
            when(dataState){
                is DataState.Loading -> showProgressbar()
                is DataState.Success -> handleDeleteSuccess()
                is DataState.Error -> handleDeleteError()
                else -> Unit
            }
        })

        viewModel.logOutState.observe(viewLifecycleOwner, { dataState ->
            when(dataState){
                is DataState.Loading -> showLogOutProgressBar()
                is DataState.Success -> handleLogOutSuccess()
                is DataState.Error -> handleLogOutError()
                else -> Unit
            }
        })
    }

    private fun showLogOutProgressBar(){
        binding.ivLogOut.visibility = View.GONE
        binding.pbLogOut.visibility = View.VISIBLE
    }

    private fun hideLogOutProgressBar(){
        binding.ivLogOut.visibility = View.VISIBLE
        binding.pbLogOut.visibility = View.GONE
    }

    private fun handleLogOutSuccess(){
        hideLogOutProgressBar()
        clearUserPreferences()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        activity?.finish()
    }

    private fun handleLogOutError(){
        hideLogOutProgressBar()
        clearUserPreferences()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        activity?.finish()
    }

    private fun clearUserPreferences() = sharedPrefs.edit().clear().apply()

    private fun handleDeleteSuccess(){
        hideProgressbar()
        refreshWalks()
    }

    private fun handleDeleteError(){
        hideProgressbar()
        showSnackBar(getString(R.string.errors__general_error))
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

    private fun handleWalksSuccess(walks: List<Walk>){
        hideProgressbar()
        walksAdapter?.submitList(walks)
        binding.gEmptyState.visibility = View.GONE
        binding.gSuccessState.visibility = View.VISIBLE
    }

    private fun handleWalksError(){
        hideProgressbar()
        binding.gEmptyState.visibility = View.VISIBLE
        binding.gSuccessState.visibility = View.GONE
    }

    private fun initTextListeners(){
        binding.etType.doOnTextChanged { text, _, _, _ ->
            when(text.toString()){
                getString(R.string.owner_walks__select_active) -> viewModel.getWalksByTypeAndOwner(type = WalkStatus.PENDING.toString(),ownerID = Session.USER_LOGGED.id)
                getString(R.string.owner_walks__select_past) -> viewModel.getWalksByTypeAndOwner(type = WalkStatus.PAST.toString(),ownerID = Session.USER_LOGGED.id)
                getString(R.string.owner_walks__select_accepted) -> viewModel.getWalksByTypeAndOwner(type = WalkStatus.ACCEPTED.toString(),ownerID = Session.USER_LOGGED.id)
                else -> Unit
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initDropdown()
    }

    private fun refreshWalks(){
        when(binding.etType.text.toString()){
            getString(R.string.owner_walks__select_active) -> viewModel.getWalksByTypeAndOwner(type = WalkStatus.PENDING.toString(),ownerID = Session.USER_LOGGED.id)
            getString(R.string.owner_walks__select_past) -> viewModel.getWalksByTypeAndOwner(type = WalkStatus.PAST.toString(),ownerID = Session.USER_LOGGED.id)
            getString(R.string.owner_walks__select_accepted) -> viewModel.getWalksByTypeAndOwner(type = WalkStatus.ACCEPTED.toString(),ownerID = Session.USER_LOGGED.id)
            else -> Unit
        }
    }

    private fun initListeners(){
        with(binding){
            fabNewWalk.setOnClickListener {
                findNavController().navigate(R.id.ownerWalksToNewWalkFragment)
            }
            ivLogOut.setOnClickListener {
                showLogOutDialog()
            }
        }
    }

    private fun showAlertDialog(walk: Walk){
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(R.string.owner_walks__delete_warining)
                setPositiveButton(R.string.ok) { _, _ ->
                    viewModel.deleteWalk(walk)
                }
                setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.show()
        }
    }

    private fun showLogOutDialog(){
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(R.string.walker_search__log_out_warining)
                setPositiveButton(R.string.ok) { _, _ ->
                    viewModel.logOut()
                }
                setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.show()
        }
    }

    override fun onOwnerWalkClickListener(walk: Walk) {
        when(walk.status){
            WalkStatus.PENDING.toString() -> Unit
            WalkStatus.PAST.toString() -> findNavController().navigate(R.id.ownerWalksFragmentToOwnerPastWalkFragment, bundleOf(WALK_BUNDLE to walk))
            WalkStatus.ACCEPTED.toString() -> findNavController().navigate(R.id.ownerWalksFragmentToOwnerSeeWalkFragment, bundleOf(WALK_BUNDLE to walk))
        }
    }

    override fun onOwnerDeleteClickListener(walk: Walk) {
        when(walk.status){
            WalkStatus.PENDING.toString() -> showAlertDialog(walk)
            WalkStatus.PAST.toString() -> Unit
            WalkStatus.ACCEPTED.toString() -> Unit
        }
    }

}