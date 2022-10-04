package com.example.kotlin_learn

import java.text.DecimalFormat

fun main(args: Array<String>) {
    val a = 1
    val b = 2

    tvaSummary()

    /*println(a.isEven())
    println(b.isEven())

    println(factorial(5))

    for (i in 1..100) {
        println(i)
        if (i == 100) {
            for (j in 100 downTo 1) {
                println(j)
            }
        }
    }

    fun useFoo() = listOf(
        foo("a"),
        foo("b", number = 1),
        foo("c", toUpperCase = true),
        foo(name = "d", number = 2, toUpperCase = true)
    )*/
}

fun helloESGI(): Unit {
    println("Hello ESGI")
}

fun foo(name: String, number: Int = 0, toUpperCase: Boolean = false) =
    (if (toUpperCase) name.uppercase() else name) + number

fun test(integer: Int): Int = integer * 5

fun Int.isEven(): Boolean {
    return this % 2 == 0
}

fun Int.isOdd(): Boolean {
    return this % 2 == 1
}

fun isVowel(srt: String): Boolean {
    return when (srt) {
        "a", "e", "i", "o", "u" -> true
        else -> false
    }
}

fun factorial(n: Int): Int {
    return if (n == 1) n else n * factorial(n - 1)
}

fun tvaCalc(price: Double, tva: Double = 20.0): Double {
    return price * 1 + (tva / 100)
}

fun tvaSummary() {
    val tvas = arrayOf(2.1, 5.5, 10.0, 20.0)
    val df = DecimalFormat("#.00")

    println("Saisissez votre montant HT :")

    val price = readln().toDouble()

    for (tva in tvas) {
        println("TVA $tva%: ${df.format(tvaCalc(price, tva))} €")
    }
}