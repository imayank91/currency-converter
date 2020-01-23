package com.app.revolutapp

import com.app.revolutapp.service.ApiService
import com.app.revolutapp.service.model.RateBase
import com.app.revolutapp.utils.RatesBaseDeserializer
import com.app.revolutapp.utils.RatesDeserializerBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.BufferedSource
import okio.Okio
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * Created by mayank on January 23 2020
 */
class RatesApiServiceTest {

    private lateinit var service: ApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun initService() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        this.service = createService()
    }

    private fun createService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                RatesDeserializerBuilder.createGsonConverter(
                    object : TypeToken<RateBase>() {

                    }.type, RatesBaseDeserializer()
                )
            )
            .build()
            .create(ApiService::class.java)
    }


    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        val inputStream = RatesApiServiceTest::class.java.classLoader!!.getResourceAsStream(
            String.format(
                "api-response/%s",
                fileName
            )
        )
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        mockWebServer.enqueue(
            mockResponse.setBody(
                (source as BufferedSource).readString(
                    StandardCharsets.UTF_8
                )
            )
        )
    }

    @Test
    @Throws(IOException::class)
    fun fetchRatesWithBaseAUD() {
        enqueueResponse("rates_response.json")
        val response = service.getLatestRates("AUD").execute()
        Assert.assertEquals(true, response.isSuccessful)
    }
}