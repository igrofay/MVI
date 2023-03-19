package com.example.mvi_proj

import org.junit.Test

import org.junit.Assert.*
import java.util.Base64

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad".length.toString())
    }
    @Test
    fun s(){
        val originalString = "Привет"
        val encodedString: String = Base64.getEncoder().encodeToString(originalString.toByteArray())
        println(encodedString)
        val decodedString = String(Base64.getDecoder().decode(encodedString))
        println(decodedString)
    }
}