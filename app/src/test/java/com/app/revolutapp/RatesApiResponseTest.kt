package com.app.revolutapp

import com.app.revolutapp.service.ApiService
import com.app.revolutapp.utils.NetworkUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by mayank on January 23 2020
 */
class RatesApiResponseTest {


    private lateinit var service: ApiService

    @Before
    fun initService() {
        this.service = NetworkUtils.buildApiService()
    }

    @Test
    fun testFetchRates() {

        val response = service.getLatestRates("EUR").execute()

        Assert.assertTrue(response.isSuccessful)
    }

}