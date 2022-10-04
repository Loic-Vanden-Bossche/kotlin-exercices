package matrices

fun main(args: Array<String>) {
    val matrix = IntMatrix(3, 3)
    matrix[0, 0] = 1
    matrix[0, 1] = 2
    println(matrix)

    val matrix2 = BooleanMatrix(10, 10)
    matrix2[0, 0] = true
    matrix2[0, 1] = true
    matrix2[2, 5] = false

    println(matrix2)

    val matrix3 = SquareMatrix<Int>(3)
    matrix3[0, 0] = 3
    matrix3[0, 1] = 10
    println(matrix3)
}

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

open class SquareMatrix<T>(n: Int) : Matrix<T>(n, n)

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
