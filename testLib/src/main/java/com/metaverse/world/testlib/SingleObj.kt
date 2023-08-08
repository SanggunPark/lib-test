package com.metaverse.world.testlib

object SingleObj {
    fun main() {
        val testClass = TestClass()
        testClass.test()
    }

    fun test(value: Int) {
        println("test $value")
    }
}