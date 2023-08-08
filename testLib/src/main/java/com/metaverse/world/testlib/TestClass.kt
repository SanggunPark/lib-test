package com.metaverse.world.testlib

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TestClass {

    fun test() {
        println("test")
    }

    suspend fun test2() {
        withContext(Dispatchers.IO) {
            println("test2")
            delay(200)
        }
    }

}