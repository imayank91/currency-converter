package com.app.revolutapp.utils

import com.app.revolutapp.service.model.Rate
import com.app.revolutapp.service.model.RateBase
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

/**
 * Created by mayank on January 22 2020
 */

class RatesBaseDeserializer : JsonDeserializer<RateBase> {

    private val keyBase = "base"
    private val keyDate = "date"
    private val keyRates = "rates"

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RateBase {
        val jsonObj = json?.asJsonObject

        return RateBase(
            jsonObj?.get(keyBase)?.asString,
            jsonObj?.get(keyDate)?.asString,
            mapRates(jsonObj?.get(keyRates)?.asJsonObject)
        )
    }


    private fun mapRates(jsonObject: JsonObject?): List<Rate> {
        val ratesList = mutableListOf<Rate>()
        jsonObject?.keySet()?.forEach {
            ratesList.add(
                Rate(
                    it,
                    jsonObject.get(it).asDouble
                )
            )
        }
        return ratesList
    }
}