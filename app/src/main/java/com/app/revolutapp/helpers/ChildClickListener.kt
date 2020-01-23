package com.app.revolutapp.helpers

import com.app.revolutapp.service.model.Rate

/**
 * Created by mayank on January 22 2020
 */
interface ChildClickListener {

    fun onChildClick(rate: Rate, position: Int)
}