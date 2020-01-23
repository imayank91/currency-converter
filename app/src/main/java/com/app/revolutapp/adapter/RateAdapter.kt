package com.app.revolutapp.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.revolutapp.R
import com.app.revolutapp.databinding.RowRateBinding
import com.app.revolutapp.helpers.ChildClickListener
import com.app.revolutapp.service.model.Rate
import com.app.revolutapp.utils.StringContract.RATE_DATE_FORMAT
import com.app.revolutapp.viewholder.RatesViewHolder
import java.text.DecimalFormat


/**
 * Created by mayank on January 22 2020
 */
class RateAdapter(
    private val context: Context,
    private val childClickListener: ChildClickListener

) : RecyclerView.Adapter<RatesViewHolder>() {

    private val dec = DecimalFormat(RATE_DATE_FORMAT)

    private val currencyPosition = mutableListOf<String>()
    private val currencyRate = HashMap<String, Rate>()

    private var amount = 1.0F

    private lateinit var recyclerView:RecyclerView

    private var multiplier: Double = 1.0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)

        val rateItemBinding: RowRateBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_rate, parent, false)

        return RatesViewHolder(rateItemBinding)
    }

    override fun getItemCount(): Int {
        return currencyPosition.size
    }


    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {

        holder.binding.rate = currencyRate[currencyPosition[position]]
        holder.binding.position = position
        holder.binding.childClick = childClickListener
        if(!holder.binding.etRate.hasFocus()){
            val rate = dec.format(currencyRate[currencyPosition[position]]!!.rate * multiplier)
            holder.binding.etRate.setText(rate)
        }

        holder.binding.etRate.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->

            if (!hasFocus) {
                return@OnFocusChangeListener
            }

            if(position !=0 ){
                childClickListener.onChildClick(holder.binding.rate!!, position)
            }

        }

        holder.binding.etRate.addTextChangedListener {
            if (holder.binding.etRate.isFocused && !it.isNullOrBlank()) {
                multiplier = it.toString().replace(",", "").toDouble()
            }
        }

    }

    fun setRateList(rateList: MutableList<Rate>) {
        if (currencyPosition.isEmpty()) {
            currencyPosition.addAll(rateList.map { it.currency })
        }

        for (rate in rateList) {
            currencyRate[rate.currency] = rate
        }


        notifyItemRangeChanged(0, currencyPosition.size - 1, amount)
    }

    fun swapItem(fromPosition: Int) {

        currencyPosition.removeAt(fromPosition).also {
            currencyPosition.add(0, it)
        }

        notifyItemMoved(fromPosition, 0)

        Handler().postDelayed({
            recyclerView.smoothScrollToPosition(0)
        },400)

    }

}