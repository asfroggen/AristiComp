package com.esaudev.aristicomp.utils

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String){
    if (url.isNotEmpty()){
        Glide.with(this.context).load(url).into(this)
    }
}

fun ImageView.loadURI(url: Uri){
    Glide.with(this.context).load(url).into(this)
}
