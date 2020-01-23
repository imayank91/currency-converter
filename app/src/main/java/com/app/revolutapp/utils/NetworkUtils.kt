package com.app.revolutapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.app.revolutapp.service.ApiService
import com.app.revolutapp.service.model.RateBase
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Created by mayank on January 22 2020
 */
object NetworkUtils {

    fun buildApiService(): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(RatesDeserializerBuilder.createGsonConverter(
                object : TypeToken<RateBase>() {

                }.type, RatesBaseDeserializer()
            ))
            .client(okHttpClient)
            .baseUrl(StringContract.BASE_URL)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    fun isConnectedToNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val capability = connectivityManager!!.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

}