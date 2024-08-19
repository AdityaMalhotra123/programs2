package hu.ait.minesweeper.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import hu.ait.minesweeper.MainActivity
import hu.ait.minesweeper.R
import hu.ait.minesweeper.model.MinesweeperModel

class MinesweeperView (context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    lateinit var paintBackground: Paint
    lateinit var paintLine: Paint
    lateinit var paintText: Paint

    private val size : Int = (context as MainActivity).returnSize()

    var bitmapFlag: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.flag)



    init {
        paintBackground = Paint()
        paintBackground.setColor(Color.BLUE)
        paintBackground.style = Paint.Style.FILL

        paintLine = Paint()
        paintLine.setColor(Color.WHITE)
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f

        //M represents a mine
        paintText = Paint()
        paintText.setColor(Color.RED)
        paintText.textSize = 100f

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paintText.textSize = (height / size).toFloat()

        bitmapFlag = Bitmap.createScaledBitmap(
            bitmapFlag, width/size, height/size, false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        drawGameArea(canvas)

        drawText(canvas)

        drawFlags(canvas)
    }


    private fun drawGameArea(canvas: Canvas) {
        // border
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)

        // 4 horizontal lines
        for (i in 1..size - 1) {
            canvas.drawLine(0f, (i * height / size).toFloat(), width.toFloat(),
                (i * height / size).toFloat(), paintLine)
        }

        // 4 vertical lines
        for (i in 1..size - 1) {
            canvas.drawLine((i * width / size).toFloat(), 0f, (i * width / size).toFloat(),
                height.toFloat(), paintLine)
        }
    }

    private fun drawText(canvas: Canvas) {
        for (i in 0..size - 1) {
            for (j in 0..size - 1) {
                val field = MinesweeperModel.getFieldContent(i, j)
                if (field.type == 2) {
                    canvas.drawText(field.minesAround.toString(), (i * width/size).toFloat(),
                        ((j + 1) * height/size).toFloat(), paintText)
                } else if (field.type == 1 && field.wasClicked == true && field.isFlagged == false) {
                    canvas.drawText("M", (i * width/size).toFloat(), ((j + 1) * height/size).toFloat(),
                        paintText)
                }
            }
        }
    }

    private fun drawFlags(canvas: Canvas) {
        for (i in 0..size - 1) {
            for (j in 0..size - 1) {
                val field = MinesweeperModel.getFieldContent(i, j)
                if (field.isFlagged == true) {
                    canvas.drawBitmap(bitmapFlag, (i * width/size).toFloat(), (j * height/size).toFloat(),
                        null)
                }
            }
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_DOWN) {

            val tX = event.x.toInt() / (width / size)
            val tY = event.y.toInt() / (height / size)
            val field = MinesweeperModel.getFieldContent(tX, tY)
            //var fieldMatrix = MinesweeperModel.fieldMatrix

            if ((context as MainActivity).isFlagModeOn()) {
                if (tX < size && tY < size && field.type == 1) {
                    field.wasClicked = true
                    field.isFlagged = true
                } else if (tX < size && tY < size) {
                    field.wasClicked = true
                    field.isFlagged = true
                    field.type = 0
                }
            } else {
                if (tX < size && tY < size && field.type == 0) {
                    field.type = 2
                    field.wasClicked = true
                } else if (tX < size && tY < size && field.type == 1) {
                    field.wasClicked = true
                    if (field.isFlagged == true) {
                        field.isFlagged = false
                    }
                }

            }
            if (MinesweeperModel.isWinner() == true) {
                (context as MainActivity).showMessage(context.getString(R.string.
                congrats_you_flagged_all_the_mines_you_win))
            }

            if (MinesweeperModel.isLoser() == true) {
                (context as MainActivity).showMessage(
                    context.getString(R.string.oh_no_that_was_a_mistake_you_lose) +
                            context.getString(R.string.try_restarting))
            }

            invalidate()
            return true
        }
        // For accessibility, consider calling performClick when appropriate
        if (event.action == MotionEvent.ACTION_UP) {
            performClick() // This is important for accessibility
        }

        return super.onTouchEvent(event)

    }

    override fun performClick(): Boolean {
        // Do any work that should happen on click
        super.performClick() // Call this method to handle the click action

        // Indicate that the click was handled
        return true
    }

    fun resetGame() {
        MinesweeperModel.resetMatrix(size)
        invalidate()
    }
}