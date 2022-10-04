package com.example.kotlin_learn

fun main(args: Array<String>) {
    ex1()
    ex2()
    ex3()
    ex4()
    ex5()
}

fun ex1() {
    // Method 1

    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var sum = 0;

    for (i in list) sum += i

    println(sum)

    // Method 2

    println(list.sum())
}

fun ex2() {
    val list = listOf("bonjour", "le", "Monde", "je", "m'appelle", "Toto", "j'habite", "en", "Bretagne")

    list.groupBy { it[0] }.map {
        (key, value) -> "${key.uppercaseChar()} = ${value.joinToString { it }}"
    }.forEach(::println)
}

fun ex3() {
    var array: Array<Int?> = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    array += 11
    array += null
    array += 23
    array += null

    array.filterNotNull().forEach(::println)
}

fun ex4() {
    val myMap = mapOf<Char, Int>('A' to 1, 'b' to 12, 'c' to 123, 'd' to 1234, 'e' to 12345)

    println(myMap['A'])
    println(myMap.getOrElse('a') { 0 })
}

fun ex5() {
    println(listOf(1, 2, 3, 4, 5).fold(0) { total, item -> total + item })
    println(listOf(1, 2, 3, 4, 5).foldRight(0) { item, total -> total + item })
    println(listOf(1, 2, 3, 4, 5).fold(1) { mul, item -> mul * item })
    println(listOf(1, 2, 3, 4, 5).foldRight(1) { item, mul -> mul * item })
    println(listOf(0, 1, 2, 3, 4, 5)
        .foldIndexed(0) { index, total, item -> if (index % 2 == 0) (total + item) else total })
    println(listOf(0, 1, 2, 3, 4, 5)
        .foldRightIndexed(0) { index, item, total -> if (index % 2 == 0) (total + item) else total })
}