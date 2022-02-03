package com.jacobdgraham.custommemorygamekotlin.models

// Enum class to hold constants which will define the size of the board and the size of each cell according to user choice
enum class BoardSize(val numCards: Int) {
    EASY(8),
    MEDIUM(16),
    HARD(24);

    // How many columns of cards will be present on the screen
    fun getWidth(): Int {
        // this refers to numCards. The when block will evaluate a list of conditions and return the most relevant one.
        return when (this) {
            EASY -> 2
            MEDIUM -> 3
            HARD -> 4
        }
    }

    fun getHeight(): Int {
        return numCards / getWidth()
    }

    fun getNumPairs(): Int {
        return numCards / 2
    }
}