package com.ob.testsimple

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class TaxTest {

    private lateinit var tax: Tax

    @Before
    fun setup() {
        tax = Tax()
    }

    @After
    fun teardown() {
        //Örneğin Room Kullanıyorsan DB'yi Kapat
    }

    @Test
    fun calculateTaxTest() {
        val netTax = tax.calculateTax(100.0,0.1)
        assertThat(netTax).isEqualTo(10)
    }

    @Test
    fun calculateIncomeTest() {
        val netIncome = tax.calculateIncome(100.0,0.1)
        assertThat(netIncome).isEqualTo(90)
    }
}

//TDD Uygulamada Geliştirme Yaptıkça Testleri Yazmak Test - Dev - Test - Dev