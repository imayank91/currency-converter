package com.app.revolutapp.utils

import com.google.gson.GsonBuilder
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

/**
 * Created by mayank on January 22 2020
 */

object RatesDeserializerBuilder {

    fun createGsonConverter(type: Type, typeAdapter: Any): Converter.Factory {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(type, typeAdapter)
        val gson = gsonBuilder.create()

        return GsonConverterFactory.create(gson)
    }
}