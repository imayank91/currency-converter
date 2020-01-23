package com.app.revolutapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.revolutapp.service.model.RateBase
import com.app.revolutapp.service.repository.RateRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by mayank on January 21 2020
 */
class RatesViewModel: ViewModel() {


    private val rateRepository = RateRepository()
    var rates = MutableLiveData<RateBase>()

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)


    var mCurrency = "EUR"

    init {
        rates = rateRepository.rateList
    }

    fun fetchRates(){
        scope.launch {
            while (true) {
                delay(1000)
                rateRepository.getRates(mCurrency)
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}