package com.esaudev.aristicomp.extensions

import androidx.fragment.app.Fragment
import com.esaudev.aristicomp.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.showSnackBar(message: String, errorMessage: Boolean = false){
    val snackbar= Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
    val snackbarView=snackbar.view
    snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE

    if(errorMessage){
        snackbarView.setBackgroundColor(
            requireActivity().color(R.color.snack_dark)
        )
    }else{
        snackbarView.setBackgroundColor(
            requireActivity().color(R.color.snack_dark)
        )
    }
    snackbar.show()
}