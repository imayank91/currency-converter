package com.app.revolutapp.service.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.revolutapp.service.ApiService
import com.app.revolutapp.service.model.RateBase
import com.app.revolutapp.utils.NetworkUtils.buildApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by mayank on January 22 2020
 */

class RateRepository {

    private val TAG = "RateRepository"

    private var apiService: ApiService = buildApiService()

    var rateList = MutableLiveData<RateBase>()

    private lateinit var mutableRateList : RateBase

    fun getRates(queryString: String) {
        apiService.getLatestRates(queryString).enqueue(object : Callback<RateBase> {
            override fun onFailure(call: Call<RateBase>, t: Throwable) {
                Log.e(TAG,"Failed to fetch rates")
            }

            override fun onResponse(call: Call<RateBase>, response: Response<RateBase>) {
                if(response.isSuccessful && response.code() == 200){
                    response.body().let {
                        mutableRateList = it!!
                        rateList.value = mutableRateList
                    }
                }
            }
        })
    }
}