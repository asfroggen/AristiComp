package com.esaudev.aristicomp.model

import com.esaudev.aristicomp.utils.Constants.DEFAULT_DOG_IMAGE
import java.util.*

data class Pet(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val age: String,
    val weight: String,
    val race: String,
    val image: String = DEFAULT_DOG_IMAGE,
    val ownerID: String
)
