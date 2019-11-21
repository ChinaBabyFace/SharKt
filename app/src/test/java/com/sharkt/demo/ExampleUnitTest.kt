package com.sharkt.demo

import android.widget.TextView
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testRun() {
        var i = 0
        var s: String

        run {
            s = "test2"
            i.and(1)
        }.and(1)
        print("i:$i,s:$s")

        i.run {
            and(2)
        }
        print("i:$i,s:$s")

        with(i) {
            minus(1)
        }
        print("i:$i,s:$s")


        s = with(i) {
            "" + and(2)
        }
        print("i:$i,s:$s")

        apply {
            i.and(1)
            s.plus("PUG")
        }
        print("i:$i,s:$s")

        s = "shark".apply {
            toUpperCase()
        }
        print("i:$i,s:$s")

        also {
            s = "shark"
            i = 99
        }
        print("i:$i,s:$s")

        s.also {
            it.plus(" its me")
        }
        s = "I".also { it.plus(" love") }.also { it.plus(" you") }
        print("i:$i,s:$s")

        let {
            s = ""
            i = 0
        }
        print("i:$i,s:$s")

        s.let {
            it.plus("shark")
        }
        print("i:$i,s:$s")

        repeat(10) {
            print("repeat：" + 10)
        }

        i = 100
        i.takeIf {
            it > 9
        }.takeUnless {
            it != null && it < 11
        }.apply {

        }

    }

}
