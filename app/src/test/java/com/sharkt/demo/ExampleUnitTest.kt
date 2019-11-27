package com.sharkt.demo

import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Test

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
    fun testCoroutine() {
//        GlobalScope.launch {
//            delay(1000)
//            print("world")
//        }
//        print("Hello ")
//        Thread.sleep(2000)
//        print(" !")

//        GlobalScope.launch { // launch a new coroutine in background and continue
//            delay(1000L)
//            println("World!")
//        }
//        println("Hello,") // main thread continues here immediately
//        runBlocking {     // but this expression blocks the main thread
//            delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
//        }
    }

    @Test
    fun runCombine() = runBlocking { // this: CoroutineScope
        runBlocking {
            delay(200L)
            println("Task from runBlocking")
        }

        coroutineScope { // Creates a coroutine scope
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // This line will be printed before the nested launch
        }

        println("Coroutine scope is over") // This line is not printed until the nested launch completes
    }

    @Test
    fun runJoin() = runBlocking {
        val job = GlobalScope.launch {
            // launch a new coroutine and keep a reference to its Job
            delay(1000)
            println("World!")
        }
        job.join()
        println("Hello,")
    }

    @Test
    fun runBlock() = runBlocking<Unit> {
        // start main coroutine
        GlobalScope.launch {
            // launch a new coroutine in background and continue
            delay(1000L)
            println("World!")
        }
        println("Hello,") // main coroutine continues here immediately
        delay(2000L)      // delaying for 2 seconds to keep JVM alive
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
