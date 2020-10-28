package com.davidtiago.coroutinesessentials

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.davidtiago.coroutinesessentials.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val cache = ComputationCache()

    private lateinit var binding: ActivityMainBinding

    private lateinit var scope: CoroutineScope

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progress.hide()
        binding.button.setOnClickListener {
            binding.textView.text = ""
            binding.progress.show()
            scope.launch {
                val number = binding.editTextNumber.text.toString().toLong()
                val count = isPrimeNo(number)
                withContext(Dispatchers.Main) {
                    if (count == 0.toLong()) {
                        binding.textView.text = "$number \n is a prime number 👍"
                    } else {
                        binding.textView.text =
                            "$number \n is NOT a prime number 👎 \n can be divided by $count numbers"
                    }
                    binding.progress.hide()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        scope = CoroutineScope(Job())
    }

    override fun onStop() {
        super.onStop()
        scope.cancel()
    }

    private suspend fun isPrimeNo(number: Long): Long = withContext(Dispatchers.Default) {
        val range = 2.toLong()..number / 2.toLong()
        var divisorCount: Long = 0
        val cacheForNumber = cache.forNumber(number)
        cacheForNumber?.let {
            Log.d("isPrimeNo", "Returning cached value")
            return@withContext cacheForNumber
        }
        for (i in range) {
            yield()
            if (number.rem(i) == 0.toLong()) {
                Log.d("isPrimeNo", "Can be divided by $i")
                divisorCount += 1
            } else {
                Log.d("isPrimeNo", "Can't be divided by $i")
            }
        }
        cache.computationCompleted(number, divisorCount)
        return@withContext divisorCount
    }
}
