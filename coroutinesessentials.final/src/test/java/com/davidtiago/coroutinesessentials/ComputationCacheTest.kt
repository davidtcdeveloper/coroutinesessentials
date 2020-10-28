package com.davidtiago.coroutinesessentials

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNull
import org.junit.Test

@ExperimentalCoroutinesApi
class ComputationCacheTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val computationCache = ComputationCache(testDispatcher)

    @Test
    fun `forNumber should return null when empty cache`() =
        testDispatcher.runBlockingTest {
            val forNumber = computationCache.forNumber(1)
            assertThat(forNumber, IsNull())
        }

    @Test
    fun `forNumber should return cached value when computation completed`() =
        testDispatcher.runBlockingTest {
            computationCache.computationCompleted(12, 4)
            val forNumber = computationCache.forNumber(12)
            assertThat(forNumber, IsEqual(4))
        }
}
