package com.ob.hilt

import javax.inject.Inject
import javax.inject.Singleton

//Constructor Injection
@Singleton
//Biz Bu Sınıfı Aktivite İçerisinde Kullandığımız İçin Main Activity deki Tablodan
// Eşit Ve Üst Bir Kapsam Seçmemiz Gerekir. Minimum @ActivityScoped ve Üstünü Seçebiliriz.
class Musician
    @Inject constructor(instrument: Instrument, band: Band) {
    fun sing(name: String) {
        println("${name} Singing")
    }
}