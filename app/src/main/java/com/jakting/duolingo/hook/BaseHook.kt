package com.jakting.duolingo.hook

abstract class BaseHook {
    var isInit: Boolean = false
    abstract fun init()
}