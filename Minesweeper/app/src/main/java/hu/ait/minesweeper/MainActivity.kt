package hu.ait.minesweeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import hu.ait.minesweeper.databinding.ActivityMainBinding
import hu.ait.minesweeper.model.MinesweeperModel
import hu.ait.minesweeper.view.MinesweeperView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    public val size : Int = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MinesweeperModel.initGameArea(size)

        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toggle.setOnClickListener {
            if (!isFlagModeOn()) {
                Snackbar.make(binding.root, getString(R.string.try_mode), Snackbar.LENGTH_LONG).setAction(
                    "Undo") {}.show()
            }
            else {
                Snackbar.make(binding.root, getString(R.string.flag_mode), Snackbar.LENGTH_LONG).setAction(
                    "Undo") {}.show()
            }
        }

        binding.btnRestart.setOnClickListener {
            binding.MinesweeperView.resetGame()
            binding.tvResult.text = ""
            binding.toggle.setChecked(false)
        }

    }

    public fun showMessage(msg: String) {
        binding.tvResult.text = msg
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).setAction("Undo") {}.show()
    }
    public fun isFlagModeOn() : Boolean {
        return  binding.toggle.isChecked
    }
    public fun returnSize() : Int{
        return size
    }
}