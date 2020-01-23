package com.app.revolutapp.service

import com.app.revolutapp.service.model.RateBase
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by mayank on January 21 2020
 */

interface ApiService {

    @GET("latest")
    fun getLatestRates(@Query("base") base: String) : Call<RateBase>

}
