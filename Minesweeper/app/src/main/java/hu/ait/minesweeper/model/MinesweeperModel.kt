package hu.ait.minesweeper.model

import hu.ait.minesweeper.MainActivity

object MinesweeperModel {

    lateinit var fieldMatrix: Array<Array<Field>>

    private var matrixSize = 0

    fun initGameArea(size: Int) {
        fieldMatrix= Array(size){
            Array(size) {
            Field(0, 0, false, false)}
        }
        matrixSize = size

        //hardcoded mines
        fieldMatrix[0][3].type = 1
        fieldMatrix[2][4].type = 1
        fieldMatrix[4][1].type = 1

        countMinesAround()
    }

    fun resetMatrix(size: Int) {
        initGameArea(size)
    }

    fun countMinesAround() {
        for (i in 0..matrixSize - 1) {
            for (j in 0..matrixSize - 1) {
                if (fieldMatrix[i][j].type == 1) {
                    if (i <= matrixSize - 2) {
                        fieldMatrix[i + 1][j].minesAround += 1
                    }
                    if (i >= 1) {
                        fieldMatrix[i - 1][j].minesAround += 1
                    }
                    if (j <= matrixSize - 2) {
                        fieldMatrix[i][j + 1].minesAround += 1
                    }
                    if (j >= 1) {
                        fieldMatrix[i][j - 1].minesAround += 1
                    }
                    if (i >= 1 && j >= 1) {
                        fieldMatrix[i - 1][j - 1].minesAround += 1
                    }
                    if (i <= matrixSize - 2 && j <= matrixSize - 2) {
                        fieldMatrix[i + 1][j + 1].minesAround += 1
                    }
                    if (i >= 1 && j <= matrixSize - 2) {
                        fieldMatrix[i - 1][j + 1].minesAround += 1
                    }
                    if (i <= matrixSize - 2 && j >= 1) {
                        fieldMatrix[i + 1][j - 1].minesAround += 1
                    }

                }
            }
        }
    }

    fun getFieldContent(x: Int, y: Int) = fieldMatrix[x][y]

    fun isWinner(): Boolean {
        for (i in 0..matrixSize - 1) {
            for (j in 0..matrixSize - 1) {
                if (fieldMatrix[i][j].type == 1 && fieldMatrix[i][j].isFlagged == false) {
                    return false
                }
            }
        }
        return true
    }

    fun isLoser(): Boolean {
        for (i in 0..matrixSize - 1) {
            for (j in 0..matrixSize - 1) {
                if (fieldMatrix[i][j].type != 1 && fieldMatrix[i][j].isFlagged == true) {
                    return true
                }
                if (fieldMatrix[i][j].type == 1 && fieldMatrix[i][j].isFlagged == false &&
                    fieldMatrix[i][j].wasClicked == true) {
                    return true
                }

            }
        }
        return false
    }
}

data class Field(var type: Int, var minesAround: Int, var isFlagged: Boolean, var wasClicked: Boolean)