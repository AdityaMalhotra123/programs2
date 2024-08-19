package hu.ait.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import hu.ait.simplecalculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.firstButton.setOnClickListener {
            calculateValue(Int::plus)

        }

        binding.secondButton.setOnClickListener {
            calculateValue(Int::minus)

        }
    }

    private fun calculateValue(op: (Int, Int)->Int) {
        try {
            if (binding.firstNumber.text.isNotEmpty()) {

                val num1 = binding.firstNumber.text.toString().toInt()
                val num2 = binding.secondNumber.text.toString().toInt()
                var res: Int = op(num1, num2)
                if (op.toString().contains("plus")) {
                    Toast.makeText(this, "added $num1 and $num2",Toast.LENGTH_SHORT).show()
                }

                binding.result.text = getString(R.string.text_result, res)


            } else {
                binding.firstNumber.error = "This field cannot be empty. "
            }
        } catch (e: Exception) {
            binding.result.text = "Error: ${e.message}"
        }
    }
}

