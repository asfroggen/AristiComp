package com.esaudev.aristicomp.walker.ui.search_walks

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.auth.ui.LoginActivity
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
import javax.inject.Inject

@AndroidEntryPoint
class WalkerSearchFragment : Fragment(), WalkerWalkAdapter.OnWalkerWalkClickListener {

    private var _binding: FragmentWalkerSearchBinding? = null
    private val binding: FragmentWalkerSearchBinding
        get() = _binding!!

    private val viewModel: WalkerSearchViewModel by viewModels()
    private var walksAdapter: WalkerWalkAdapter? = null

    @Inject
    lateinit var sharedPrefs: SharedPreferences

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
        initListeners()
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

    private fun initListeners(){
        with(binding){
            ivLogOut.setOnClickListener {
                showAlertDialog()
            }
        }
    }

    private fun showAlertDialog(){
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

    override fun onWalkerWalkClickListener(walk: Walk) {
        if (walk.fullDate.hasNotPassed()){
            findNavController().navigate(R.id.walkerSearchToWalkerWalkFragment, bundleOf(WALK_BUNDLE to walk))
        } else {
            showSnackBar(getString(R.string.walker_search__date_overdue_disclaimer))
        }

    }


}