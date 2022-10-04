package com.example.kotlin_learn

import kotlin.text.StringBuilder

data class DataUser(var firstName: String, var lastName: String) {

    override fun toString(): String {
        return "$firstName $lastName"
    }
}

class User(private var firstName: String, private var lastName: String) {
    override fun toString(): String {
        return "$firstName $lastName"
    }

    override fun equals(other: Any?): Boolean {
        if (other is User) {
            return other.firstName == firstName && other.lastName == lastName
        }
        return false
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        return result
    }

    companion object {
        fun hello() {
            println("Hello World")
        }
    }
}

class LocalWeather(temperature: Float, location: String): Weather(temperature, location) {
    override fun isHot(): Boolean {
        return temperature > 32
    }
}

open class Weather(val temperature: Float, private val location: String) {
    constructor(w: Weather) : this(w.temperature, w.location)

    override fun toString(): String {
        return "Température de $location:  $temperature"
    }

    fun isCold(): Boolean {
        return temperature < 0
    }

    open fun isHot(): Boolean {
        return temperature > 25
    }
}

enum class NetworkStateEnum {
    INITIAL, IN_PROGRESS, SUCCESS, ERROR
}

sealed class NetworkState {
    object INITIAL : NetworkState()
    object IN_PROGRESS : NetworkState()
    data class SUCCESS(val data: String) : NetworkState()
    data class ERROR(val exception: Exception) : NetworkState()
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

fun main(args: Array<String>) {
    val user = DataUser("John", "Doe")
    val user2 = DataUser("John", "Doe")

    val w = Weather(10.0f, "Paris")
    val w2 = Weather(w)

    val w3 = object : Weather(10.0f, "Paris") {
        override fun isHot(): Boolean {
            return temperature > 40
        }
    }

    val mat = IntMatrix(10, 10)
    mat[0, 0] = 1
    mat[0, 4] = 4

    val mat2 = IntMatrix(10, 10)
    mat2[0, 0] = 1
    mat2[0, 4] = 2

    println(mat * mat2)

    println(w)
    println(w2)

    println(user == user2)

    println(user)
}