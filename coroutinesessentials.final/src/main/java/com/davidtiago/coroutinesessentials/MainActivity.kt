package com.davidtiago.coroutinesessentials

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davidtiago.coroutinesessentials.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progress.hide()
        binding.button.setOnClickListener {
            binding.textView.text = ""
            binding.progress.show()
            val number = binding.editTextNumber.text.toString().toLong()
            val count = isPrimeNo(number)
            if (count > 0) {
                binding.textView.text = "$number \n is a prime number 👍"
            } else {
                binding.textView.text = "$number \n is NOT a prime number 👎 \n can be divided by $count numbers"
            }
            binding.progress.hide()
        }
    }
}

fun isPrimeNo(number: Long): Long {
    val range = 1.toLong()..number / 2.toLong()
    var divisorCount: Long = 0
    for (i in range) {
        if (number.rem(i) == 0.toLong()) {
            print("Can be divided by $i")
            divisorCount += 1
        }
    }
    return divisorCount
}
