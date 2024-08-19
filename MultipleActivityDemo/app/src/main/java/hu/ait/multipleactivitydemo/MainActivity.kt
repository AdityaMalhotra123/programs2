package hu.ait.multipleactivitydemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.multipleactivitydemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    companion object {
        const val KEY_DATA = "KEY_DATA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            val intentDetails = Intent()
            intentDetails.setClass(this, DetailsActivity::class.java)
            intentDetails.putExtra(
               KEY_DATA, binding.etData.text.toString()
            )
            startActivity(intentDetails)
        }
    }
}