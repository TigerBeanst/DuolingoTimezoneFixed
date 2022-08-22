package com.jakting.duolingo.hook

import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.hookMethod
import java.time.ZoneId
import java.util.*

object TimeZoneHook : BaseHook() {
    override fun init() {
        // Example for findMethod
        findMethod("java.util.TimeZone") {
            name == "getID"
        }.hookAfter {
            it.result = "Asia/Hong_Kong"
        }

    }
}