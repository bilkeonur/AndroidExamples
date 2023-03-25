package com.ob.testsimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tax = Tax()
        val income = tax.calculateTax(100.0,0.1)
        val netTax = tax.calculateIncome(100.0,0.1)
    }
}