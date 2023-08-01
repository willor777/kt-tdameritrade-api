package com.willor777.td_api.common

import java.util.logging.Level
import java.util.logging.Logger


internal val Log: Logger = Logger.getGlobal()


internal fun Logger.w(tag: String, msg: String) {
    Logger.getGlobal().warning("$tag\t$msg")
}


internal fun Logger.d(tag: String, msg: String) {
    Logger.getGlobal().log(Level.parse("DEBUG"), "$tag\t$msg")
}