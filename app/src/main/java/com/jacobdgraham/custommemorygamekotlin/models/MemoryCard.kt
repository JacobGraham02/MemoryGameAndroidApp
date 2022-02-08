package com.jacobdgraham.custommemorygamekotlin.models

data class MemoryCard(
    // Identifier for the card represents uniqueness of memory icon; the underlying integer id of the memory card
    val identifier: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false
)