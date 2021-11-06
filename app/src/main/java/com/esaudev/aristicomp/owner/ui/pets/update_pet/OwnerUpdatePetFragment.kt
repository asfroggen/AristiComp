package com.esaudev.aristicomp.owner.ui.pets.update_pet

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
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.esaudev.aristicomp.R
import com.esaudev.aristicomp.databinding.FragmentOwnerUpdatePetBinding
import com.esaudev.aristicomp.extensions.*
import com.esaudev.aristicomp.model.Pet
import com.esaudev.aristicomp.utils.Constants
import com.esaudev.aristicomp.utils.Constants.PET_BUNDLE
import com.esaudev.aristicomp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_owner.*
import java.io.IOException

@AndroidEntryPoint
class OwnerUpdatePetFragment : Fragment() {

    private var _binding: FragmentOwnerUpdatePetBinding? = null
    private val binding: FragmentOwnerUpdatePetBinding
        get() = _binding!!

    private val viewModel: OwnerUpdatePetViewModel by viewModels()
    private var mPet: Pet = Pet()

    private var mSelectedImageURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mPet = it.getParcelable(PET_BUNDLE)?: Pet()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentOwnerUpdatePetBinding
            .inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObservers()
        initListeners()
    }

    private fun initObservers(){
        viewModel.updatePetState.observe(viewLifecycleOwner, { dataState ->
            when(dataState){
                is DataState.Loading -> showUpdateProgressBar()
                is DataState.Success -> handleUpdateSuccess()
                is DataState.Error -> handleUpdateError()
                else -> Unit
            }
        })
        viewModel.deletePetState.observe(viewLifecycleOwner, { dataState ->
            when(dataState){
                is DataState.Loading -> showDeleteProgressBar()
                is DataState.Success -> handleDeleteSuccess()
                is DataState.Error -> handleDeleteError()
                else -> Unit
            }
        })
    }

    private fun initListeners(){
        with(binding){
            mbBack.setOnClickListener { activity?.onBackPressed() }
            mbSavePet.setOnClickListener { handleUpdatePet() }
            mbDeletePet.setOnClickListener { handleDeletePet() }
            ivImage.setOnClickListener { handleImageSelection() }
        }
    }

    private fun handleUpdatePet(){
        if (mSelectedImageURI == null){
            if (areDataMissing()){
                showSnackBar(getString(R.string.owner_pets__new_pet_fill_data))
            } else{
                viewModel.updatePet(getPet())
            }
        } else {
            showUpdateProgressBar()
            viewModel.uploadPetImage(requireActivity(), mSelectedImageURI, Constants.DOG_IMAGE, this)
        }
    }

    private fun handleDeletePet(){
        showAlertDialog()
    }

    private fun showAlertDialog(){
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(R.string.owner_update_pet__delete_warning)
                setPositiveButton(R.string.ok) { _, _ ->
                    viewModel.deletePet(mPet)
                }
                setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.show()
        }
    }

    fun uploadImageSuccess(imageURL: String){
        if (areDataMissing()){
            showSnackBar(getString(R.string.owner_pets__new_pet_fill_data))
        } else{
            viewModel.updatePet(getPetWithImage(imageURL))
        }
    }

    fun uploadImageFailure(){
        hideUpdateProgressBar()
        showSnackBar(getString(R.string.errors__general_error))
    }

    private fun handleUpdateSuccess(){
        hideUpdateProgressBar()
        activity?.onBackPressed()
        showSnackBar(getString(R.string.owner_pets__new_pet_success))
    }

    private fun handleDeleteSuccess(){
        hideDeleteProgressBar()
        activity?.onBackPressed()
        showSnackBar(getString(R.string.owner_update_pet__delete_success))
    }

    private fun handleUpdateError(){
        hideUpdateProgressBar()
        showSnackBar(getString(R.string.errors__general_error))
    }

    private fun handleDeleteError(){
        hideDeleteProgressBar()
        showSnackBar(getString(R.string.errors__general_error))
    }

    private fun areDataMissing(): Boolean{
        return binding.etName.text.isNullOrEmpty() ||
                binding.etAge.text.isNullOrEmpty() ||
                binding.etRace.text.isNullOrEmpty() ||
                binding.etWeight.text.isNullOrEmpty()
    }

    private fun showUpdateProgressBar(){
        with(binding){
            pbSavePet.visibility = View.VISIBLE
            mbSavePet.text = ""
            mbSavePet.isEnabled = false
        }
    }

    private fun hideUpdateProgressBar(){
        with(binding){
            pbSavePet.visibility = View.GONE
            mbSavePet.text = getString(R.string.owner_pets__new_pet_save_button)
            mbSavePet.isEnabled = true
        }
    }

    private fun showDeleteProgressBar(){
        with(binding){
            pbDeletePet.visibility = View.VISIBLE
            mbDeletePet.text = ""
            mbDeletePet.isEnabled = false
        }
    }

    private fun hideDeleteProgressBar(){
        with(binding){
            pbDeletePet.visibility = View.GONE
            mbDeletePet.text = getString(R.string.owner_update_pet__delete_success)
            mbDeletePet.isEnabled = true
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
            image = mPet.image,
            ownerID = mPet.ownerID
        )
    }

    private fun getPetWithImage(imageURL: String): Pet{
        return Pet(
            name = binding.etName.text.toString(),
            weight = binding.etWeight.text.toString(),
            race = binding.etRace.text.toString(),
            age = binding.etAge.text.toString(),
            image = imageURL,
            ownerID = mPet.ownerID
        )
    }

    private fun initView(){
        with(binding){
            etName.setText(mPet.name)
            etAge.setText(mPet.age)
            etWeight.setText(mPet.weight)
            etRace.setText(mPet.race)

            ivImage.load(mPet.image)
        }
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