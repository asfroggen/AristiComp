package com.esaudev.aristicomp.model

import java.util.*

data class Walk (
    val id: String = UUID.randomUUID().toString(),
    val petID: String,
    val ownerID: String,
    val status: String,
    val walkerID: String,
    val date: String,
    val time: String,
    val walkDuration: String,
    val ownerName: String,
    val walkerName: String,
    val comments: String
        )