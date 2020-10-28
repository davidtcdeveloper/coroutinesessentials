package com.davidtiago.coroutinesessentials

class ComputationCache {

    private val cache = mutableMapOf<Long, Long>()

    suspend fun forNumber(number: Long): Long? = cache[number]

    suspend fun computationCompleted(number: Long, divisorCount: Long) {
        cache[number] = divisorCount
    }
}
