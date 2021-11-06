package com.esaudev.aristicomp.owner.ui.pets.new_pet

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentOwnerNewPetBinding
import com.esaudev.aristicomp.databinding.FragmentOwnerPetsBinding
import com.esaudev.aristicomp.extensions.*
import com.esaudev.aristicomp.model.Pet
import com.esaudev.aristicomp.model.Session
import com.esaudev.aristicomp.utils.Constants
import com.esaudev.aristicomp.utils.Constants.DEFAULT_DOG_IMAGE
import com.esaudev.aristicomp.utils.Constants.DOG_IMAGE
import com.esaudev.aristicomp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_owner.*
import java.io.IOException

@AndroidEntryPoint
class OwnerNewPetFragment : Fragment() {

    private var _binding: FragmentOwnerNewPetBinding? = null
    private val binding: FragmentOwnerNewPetBinding
        get() = _binding!!

    private val viewModel: OwnerNewPetViewModel by viewModels()

    private var mSelectedImageURI: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentOwnerNewPetBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initObservers(){
        viewModel.savePetState.observe(viewLifecycleOwner, { dataState ->
            when(dataState){
                is DataState.Loading -> showProgressBar()
                is DataState.Success -> handleSuccess()
                is DataState.Error -> handleError()
                else -> Unit
            }
        })
    }

    private fun initListeners(){
        binding.mbBack.setOnClickListener { activity?.onBackPressed() }
        binding.mbSavePet.setOnClickListener { handleSavePet() }
        binding.ivImage.setOnClickListener { handleImageSelection() }
    }

    private fun handleSavePet(){
        if (mSelectedImageURI == null){
            if (areDataMissing()){
                showSnackBar(getString(R.string.owner_pets__new_pet_fill_data))
            } else{
                viewModel.savePet(getPet())
            }
        } else {
            showProgressBar()
            viewModel.uploadPetImage(requireActivity(), mSelectedImageURI, DOG_IMAGE, this)
        }
    }

    fun uploadImageSuccess(imageURL: String){
        if (areDataMissing()){
            showSnackBar(getString(R.string.owner_pets__new_pet_fill_data))
        } else{
            viewModel.savePet(getPetWithImage(imageURL))
        }
    }

    fun uploadImageFailure(){
        hideProgressBar()
        showSnackBar(getString(R.string.errors__general_error))
    }

    private fun handleSuccess(){
        hideProgressBar()
        activity?.onBackPressed()
        showSnackBar(getString(R.string.owner_pets__new_pet_success))
    }

    private fun handleError(){
        hideProgressBar()
        showSnackBar(getString(R.string.errors__general_error))
    }

    private fun areDataMissing(): Boolean{
        return binding.etName.text.isNullOrEmpty() ||
        binding.etAge.text.isNullOrEmpty() ||
        binding.etRace.text.isNullOrEmpty() ||
        binding.etWeight.text.isNullOrEmpty()
    }

    private fun showProgressBar(){
        with(binding){
            pbSavePet.visibility = View.VISIBLE
            mbSavePet.text = ""
            mbSavePet.isEnabled = false
        }
    }

    private fun hideProgressBar(){
        with(binding){
            pbSavePet.visibility = View.GONE
            mbSavePet.text = getString(R.string.owner_pets__new_pet_save_button)
            mbSavePet.isEnabled = true
        }
    }

    private fun handleImageSelection(){
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Constants.showImageChooser(this)
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Constants.READ_STORAGE_PERMISSION_CODE)
        }
    }

    private fun getPet(): Pet{
        return Pet(
            name = binding.etName.text.toString(),
            age = binding.etAge.text.toString(),
            weight = binding.etWeight.text.toString(),
            race = binding.etRace.text.toString(),
            ownerID = Session.USER_LOGGED.id
        )
    }

    private fun getPetWithImage(imageURL: String): Pet{
        return Pet(
            name = binding.etName.text.toString(),
            weight = binding.etWeight.text.toString(),
            race = binding.etRace.text.toString(),
            age = binding.etAge.text.toString(),
            image = imageURL,
            ownerID = Session.USER_LOGGED.id
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            // showErrorSnackBar("Permisos asignados correctamente.", false)
            Constants.showImageChooser(this)
        }else{
            // Displaying another toast if permission is not granted
            activity?.toast(getString(R.string.read_storage_permission_denied))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE){
                if (data != null){
                    mSelectedImageURI=data.data!!
                    try {
                        //Glide.with(requireContext()).load(mSelectedImageURI).centerCrop().into(binding.ivTeamImage)
                        binding.ivImage.loadURI(mSelectedImageURI!!)
                    }catch (e: IOException){
                        e.printStackTrace()
                        activity?.toast(getString(R.string.image_selection_failed))
                        // Toast.makeText(this@AddProductActivity, R.string.,Toast.LENGTH_SHORT).show()
                    }
                }
            }else if(resultCode== Activity.RESULT_CANCELED){
                Log.e("Request cancelled", "Image selection cancelled")
            }
        }
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