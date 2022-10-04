package minesweeper

fun main(args: Array<String>) {
    val game = MinesweeperBoard(10, 10, 10)

    println(game)
    println(game.getBoardHints())
}

// Minesweeper

class MinesweeperBoard(ny: Int, nx: Int, minesToPlant: Int = 10): BooleanMatrix(ny, nx) {
    init {
        require(minesToPlant <= ny * nx) { "Too many mines" }
        for (i in 0 until ny) {
            for (j in 0 until nx) {
                this[i, j] = false
            }
        }

        this.randomlyPantMines(10)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (i in 0 until ny) {
            for (j in 0 until nx) {
                sb.append("${if (this[i, j] == true) "*" else "."}${if (j < nx - 1) "" else ""}")
            }
            sb.appendLine()
        }
        return sb.toString()
    }

    fun getBoardHints(): IntMatrix {
        val hints = object : IntMatrix(ny, nx) {
            override fun toString(): String {
                val sb = StringBuilder()
                for (i in 0 until ny) {
                    for (j in 0 until nx) {
                        sb.append("${if (this[i, j] == -1) "*" else (if (this[i, j] == 0) "." else this[i, j])}${if (j < nx - 1) "" else ""}")
                    }
                    sb.appendLine()
                }
                return sb.toString()
            }
        }

        for (row in 0 until ny) {
            for (column in 0 until nx) {
                hints[row, column] = getHint(row, column)
            }
        }
        return hints
    }

    private fun randomlyPantMines(nbMines: Int) {
        for (i in 0 until nbMines) {
            var x = (0 until ny).random()
            var y = (0 until nx).random()
            while (this[x, y] == true) {
                x = (0 until ny).random()
                y = (0 until nx).random()
            }

            this[x, y] = true
        }
    }

    private fun getHint(row: Int, column: Int): Int {
        if (this[row, column] == true) {
            return -1
        }

        var hint = 0
        for (i in row - 1..row + 1) {
            for (j in column - 1..column + 1) {
                if (i in 0 until ny && j in 0 until nx && this[i, j] == true) {
                    hint++
                }
            }
        }
        return hint
    }
}

// Matrix classes

abstract class Matrix<T>(val ny: Int, val nx: Int) {
    private val matrix: List<MutableList<T?>> = List(ny) { MutableList(nx) { null } }

    init {
        require(ny > 0 && nx > 0) { "Dimensions must be positive" }
    }

    fun list(): List<T?> {
        return matrix.flatten()
    }

    operator fun get(y: Int, x: Int): T? {
        return matrix[y][x]
    }

    operator fun set(y: Int, x: Int, value: T?) {
        matrix[y][x] = value
    }

    fun isSquare(): Boolean {
        return ny == nx
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (i in 0 until ny) {
            sb.append("[")
            for (j in 0 until nx) {
                sb.append("${matrix[i][j] ?: "-"}${if (j < nx - 1) ", " else ""}")
            }
            sb.append("]")
            sb.appendLine()
        }
        return sb.toString()
    }
}

open class IntMatrix(ny: Int, nx: Int) : Matrix<Int>(ny, nx) {
    operator fun times(other: IntMatrix): IntMatrix {
        require(this.nx == other.ny) { "Matrices dimensions must agree" }
        val result = IntMatrix(this.ny, other.nx)
        for (i in 0 until this.ny) {
            for (j in 0 until other.nx) {
                var sum = 0
                for (k in 0 until this.nx) {
                    sum += (this[i, k] ?: 0) * (other[k, j] ?: 0)
                }
                result[i, j] = sum
            }
        }
        return result
    }

    operator fun timesAssign(other: IntMatrix) {
        require(this.nx == other.ny) { "Matrices dimensions must agree" }
        for (i in 0 until this.ny) {
            for (j in 0 until other.nx) {
                var sum = 0
                for (k in 0 until this.nx) {
                    sum += (this[i, k] ?: 0) * (other[k, j] ?: 0)
                }
                this[i, j] = sum
            }
        }
    }
}

open class BooleanMatrix(ny: Int, nx: Int) : Matrix<Boolean>(ny, nx)


