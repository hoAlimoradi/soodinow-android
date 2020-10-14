package com.paya.presentation

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TestClass{
	
	
	
	fun main() = runBlocking {
		val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
			delay(1000L)
			println("World!")
		}
		println("Hello,")
		job.join() // wait until child coroutine completes
	}
	
}