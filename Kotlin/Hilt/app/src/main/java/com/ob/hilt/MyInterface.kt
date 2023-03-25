package com.ob.hilt

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Qualifier
import javax.inject.Singleton

interface MyInterface {
    fun myPrintFunction(): String
}

//1. Yöntem
/*
@InstallIn(ActivityComponent::class)
@Module
abstract class MyModule {
    @ActivityScoped //Yukarısı ActivityComponent olduğu için
    @Binds
    abstract fun bindingFunction(myImplementor: InterfaceImplementor): MyInterface
}
*/

//2. Yöntem
@InstallIn(ActivityComponent::class)
@Module
class MyModule {
    @FirstImplementor
    @ActivityScoped
    @Provides
    fun providerFunction(): MyInterface {
        return InterfaceImplementor()
    }

    @SecondImplementer
    @ActivityScoped
    @Provides
    fun secondProviderFunction(): MyInterface {
        return SecondInterfaceImplementor()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FirstImplementor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SecondImplementer

