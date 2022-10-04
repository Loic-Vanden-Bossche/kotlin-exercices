package com.example.kotlin_learn

fun main(args: Array<String>) {
    println(isPangramme("The quick brown fox jumps over the lazy dog"))
    println(isPangramme("Portez ce vieux whisky au juge blond qui fume"))
    println(isPangramme("C'est un pangramme"))

    val game = TicTacToe()
    // game.run()

    val mineGame = MinesweperBoard(50, 30, 90)

    mineGame.print()
}

fun isPangramme(sentence: String): Boolean {
    val alphabet = "abcdefghijklmnopqrstuvwxyz"
    return alphabet.all { sentence.contains(it, true) }
}

class TicTacToe: SquareMatrix<Int>(3) {
    init {
        for (i in 0..2) {
            for (j in 0..2) {
                this[i, j] = 0
            }
        }
    }

    private var currentPlayer = getRandomPLayer()
    private var isFinished = false

    private fun play(x: Int, y: Int) {
        require(!isFinished) { "Game is finished" }
        require(this[x, y] == 0) { "Cell is not empty" }
        this[x, y] = currentPlayer
        currentPlayer = if (currentPlayer == 1) 2 else 1
        println(whoWon())
        isFinished = whoWon() != -1
    }

    private fun whoWon(): Int? {
        for (i in 0..2) {
            if (this[i, 0] == this[i, 1] && this[i, 1] == this[i, 2] && this[i, 0] != 0) {
                return this[i, 0]
            }
            if (this[0, i] == this[1, i] && this[1, i] == this[2, i] && this[0, i] != 0) {
                return this[0, i]
            }
        }
        if (this[0, 0] == this[1, 1] && this[1, 1] == this[2, 2] && this[0, 0] != 0) {
            return this[0, 0]
        }
        if (this[0, 2] == this[1, 1] && this[1, 1] == this[2, 0] && this[0, 2] != 0) {
            return this[0, 2]
        }
        println("this.list()")
        println(this.list())
        if (this.list().all { it != 0 }) {
            return 0
        }
        return -1
    }

    private fun getRandomPLayer(): Int {
        return (1..2).random()
    }

    fun run() {
        while (!isFinished) {
            println(this)
            println("Player $currentPlayer, enter your move (x y):")
            val (x, y) = readLine()!!.split(",").map { it.toInt() - 1 }
            play(x, y)
        }
        println(this)
        println("Player $currentPlayer won!")
    }
}

class MinesweperBoard(nx: Int, ny: Int, minesToPlant: Int? = null): BooleanMatrix(ny, nx) {
    init {
        for (i in 0 until ny) {
            for (j in 0 until nx) {
                this[i, j] = false
            }
        }

        if (minesToPlant != null) {
            randomlyPantMines(minesToPlant)
        }
    }

    private fun plantMine(x: Int, y: Int) {
        this[x, y] = true
    }

    fun randomlyPantMines(nbMines: Int) {
        for (i in 0 until nbMines) {
            var x = (0 until ny).random()
            var y = (0 until nx).random()
            while (this[x, y] == true) {
                x = (0 until ny).random()
                y = (0 until nx).random()
            }
            plantMine(x, y)
        }
    }

    private fun countNeighbours(x: Int, y: Int): Int {
        var count = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                if (this[
                            if (i + x < 0 || (i + x) >= nx) x else x + i,
                            if (j + y < 0 || (j + y) >= ny) y else y + j
                ] == true) {
                    count++
                }
            }
        }
        return count
    }

    fun print() {
        for (i in 0 until ny) {
            for (j in 0 until nx) {
                if (this[i, j] == true) {
                    print("X")
                } else {
                    print(countNeighbours(i, j))
                }
            }
            println()
        }
    }
}