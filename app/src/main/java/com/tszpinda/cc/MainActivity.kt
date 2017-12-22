package com.tszpinda.cc

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tszpinda.cc.databinding.ActivityMainBinding
//import com.tszpinda.cc.BR

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pl = Currency(name ="Polish Zloty", symbol = "PLN")
        val gbp = Currency(name ="British Pound", symbol = "GBP")
        val conversionResult = Conversion(from = pl, to = gbp, rate = 5.1f)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setVariable(BR.conversionResult, conversionResult)
        binding.executePendingBindings()

    }

}
