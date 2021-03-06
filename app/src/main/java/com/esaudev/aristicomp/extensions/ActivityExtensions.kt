package com.esaudev.aristicomp.extensions

import android.app.Activity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText

fun Activity.color(@ColorRes color : Int ) = ContextCompat.getColor(this, color)

fun Activity.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Activity.isInputEmpty(editText: TextInputEditText, showError: Boolean = false, error: String = "") : Boolean {
    return if (TextUtils.isEmpty(editText.text.toString().trim { it <= ' ' })){
        if (showError){
            editText.error = error
        }
        true
    } else {
        false
    }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}
