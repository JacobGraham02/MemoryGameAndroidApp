package com.jacobdgraham.custommemorygamekotlin.models

import com.jacobdgraham.custommemorygamekotlin.utils.DEFAULT_ICONS

// Delegate the task of generating all the memory cards; the main activity should not be responsible for that.
class MemoryGame(private val boardSize: BoardSize) {
    val cards: List<MemoryCard>
    var numPairsFound = 0

    private var indexOfSingleSelectedCard: Int? = null // Nullable integer with a default value of null

    // init keyword means "do this" when an instance of the class is initialized
    init {
        // Pass into the adapter the list of images from drawable that should make up the memory tiles in the game
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImagesDoubled = (chosenImages + chosenImages).shuffled()
        // For each element in the list of randomizedImages, perform some operation on each element and store these changed elements in a new list
        // Setting false is not required because of default parameters
        cards = randomizedImagesDoubled.map{ MemoryCard(it, false, false) }
    }

    // After we have flipped the card, we need to tell the RecyclerView adapter its contents have changed
    fun flipCard(position: Int): Boolean {
        /**
         * There are 3 possible cases:
         * 0 cards previously flipped over => flip over selected card
         * 1 card previously flipped over => flip over selected cards + check if 2 cards that are flipped over match
         * 2 cards previously flipped over => restore the cards and make them face down + flip over the selected card
         */
        var foundMatch = false
        // 0 or 2 cards previously flipped over
        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            // Exactly 1 card flipped over
            val foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)

            indexOfSingleSelectedCard = null
        }
        val card = cards[position]
        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if (cards[position1].identifier != cards[position2].identifier) {
            return false
        }
        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numPairsFound++
        return true
    }

    private fun restoreCards() {
        for (card in cards) {
            if (card.isMatched) {
                continue
            }
            card.isFaceUp = false
        }
    }
}