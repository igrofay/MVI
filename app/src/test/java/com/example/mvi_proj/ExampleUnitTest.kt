package com.example.mvi_proj

import com.example.mvi_proj.crypto.decrypt
import com.example.mvi_proj.crypto.encrypt
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.security.MessageDigest
import java.util.*

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
        val dig = MessageDigest.getInstance("SHA-256")
        val hash = dig.digest("124567".toByteArray()).toHexString()
        assertEquals(
            "76f9848f41c4c3e2bf0e8baaf2fcf4579d0b467e695989e507873f46bf6855f3",
            hash
        )
        println(
            dig.digest("124567".toByteArray())
                .map { it.toString() }.joinToString(", ")
        )
    }

    fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }

}