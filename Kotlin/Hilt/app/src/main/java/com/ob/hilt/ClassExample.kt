package com.ob.hilt

import javax.inject.Inject

class ClassExample @Inject constructor(@FirstImplementor private val myInterfaceImplementer: MyInterface, @SecondImplementer private val mySecondInterfaceImplementor: MyInterface) {

    fun myFunction(): String {
        return "Working ${myInterfaceImplementer.myPrintFunction()}"
    }

    fun secondFunction(): String {
        return "Working ${mySecondInterfaceImplementor.myPrintFunction()}"
    }
}
