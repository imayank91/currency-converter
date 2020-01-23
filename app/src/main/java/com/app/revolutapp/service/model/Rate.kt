package com.app.revolutapp.service.model

/**
 * Created by mayank on January 22 2020
 */

data class RateBase(
    val base: String?,
    val date: String?,
    var rates: List<Rate>
)

data class Rate(
    val currency: String,
    var rate: Double
)