package com.app.revolutapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.revolutapp.adapter.RateAdapter
import com.app.revolutapp.databinding.ActivityMainBinding
import com.app.revolutapp.helpers.ChildClickListener
import com.app.revolutapp.service.model.Rate
import com.app.revolutapp.utils.NetworkUtils
import com.app.revolutapp.viewmodel.RatesViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ChildClickListener {

    private lateinit var ratesViewModel: RatesViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var rateAdapter: RateAdapter

    private var baseRate: Rate = Rate("EUR", 1.0)

    private var ratesList = mutableListOf<Rate>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        setSupportActionBar(binding.mainToolbar.toolbar)
        setUpRecyclerView()

        ratesViewModel = ViewModelProviders.of(this).get(RatesViewModel::class.java)
        ratesViewModel.fetchRates()
        ratesViewModel.rates.observe(this, Observer {
            it?.let {

                ratesList.clear()
                ratesList.add(baseRate)
                ratesList.addAll(it.rates)
                rateAdapter.setRateList(ratesList)
                stopShimmer()
            }
        })

        checkInternet()
    }

    private fun setUpRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        rateAdapter = RateAdapter(applicationContext, this)
        binding.recyclerView.adapter = rateAdapter
    }

    override fun onChildClick(rate: Rate, position: Int) {
        if (!binding.recyclerView.isComputingLayout) {
            baseRate = rate
            rateAdapter.swapItem(position)
            ratesViewModel.mCurrency = baseRate.currency
        }

    }

    private fun stopShimmer() {
        binding.loaderLayout.shimmerLayout.stopShimmer()
        binding.loaderLayout.rootView.visibility = View.GONE
    }

    private fun checkInternet() {
        if (!NetworkUtils.isConnectedToNetwork(applicationContext)) {
            Snackbar.make(
                binding.root,
                getString(R.string.no_internet_connection),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}
