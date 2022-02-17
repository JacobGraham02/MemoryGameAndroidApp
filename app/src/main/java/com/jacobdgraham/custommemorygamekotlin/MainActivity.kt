package com.jacobdgraham.custommemorygamekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jacobdgraham.custommemorygamekotlin.models.BoardSize
import com.jacobdgraham.custommemorygamekotlin.models.MemoryCard
import com.jacobdgraham.custommemorygamekotlin.models.MemoryGame
import com.jacobdgraham.custommemorygamekotlin.utils.DEFAULT_ICONS
//TODO: Start from 1:01:51 in youtube video: https://www.youtube.com/watch?v=C2DBDZKkLss&list=LL&index=15&t=2596s
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var clRoot : ConstraintLayout
    private lateinit var memoryGame: MemoryGame
    private lateinit var adapter: MemoryBoardAdapter
    // The lateinit keyword allows you to avoid initializing a property when an object is constructed.
    private lateinit var recyclerViewBoard: RecyclerView
    private lateinit var textViewNumPairs: TextView
    private lateinit var textViewNumMoves: TextView
    private var boardSize: BoardSize = BoardSize.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Look in the layout directory of project resources and select activity_main layout.
        // By default, the constraint layout is the root element for activity_main XML.
        // Layout weight is how much space the parent element should allocate for a child element

        // findViewById finds a view (element) in the content view activity_main.
        clRoot = findViewById(R.id.clRoot)
        recyclerViewBoard = findViewById(R.id.recyclerViewBoard)
        textViewNumPairs = findViewById(R.id.txtViewNumPairs)
        textViewNumMoves = findViewById(R.id.txtViewNumMoves)

        // Construct our memory game by using the newly created class MemoryCard which builds all of the memory cards for us
         memoryGame = MemoryGame(boardSize)

        // LayoutManager: Measures and positions item views.
        // Adapter: Providing a binding for the data set to the views of the RecyclerView.
        // 4th parameter is an anonymous class of type OnClickListener interface
        adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object: MemoryBoardAdapter.CardClickListener {
            override fun onCardClicked(position: Int) {
                updateGameWithCardFlip(position)
            }
        })
        recyclerViewBoard.adapter = adapter
        recyclerViewBoard.setHasFixedSize(true) // RecyclerView can perform optimizations if it can know in advance that RecyclerView's size is not affected by the
        // adapter contents. RecyclerView size can still change depending on other factors like parent size, but the size calculation cannot depend on the size of
        // its children or contents of its adapter, and only the number of items in the adapter.
        // We are passing in this because the current class is a context.
        recyclerViewBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }

    private fun updateGameWithCardFlip(position: Int) {
        if (memoryGame.haveWon()) {
            // Snackbar shows up at bottom of screen and displays a message on the screen for a user
            Snackbar.make(clRoot, "You already won", Snackbar.LENGTH_LONG).show()
            // Alert the user of an invalid move because the game has been won
            return
        }
        if (memoryGame.isCardFaceUp(position)) {
            Snackbar.make(clRoot, "Invalid move", Snackbar.LENGTH_SHORT).show()
            return
        }

        // If the card has been flipped
        if (memoryGame.flipCard(position)) {
            Log.i(TAG, "Found a match! Number of pairs found: ${memoryGame.numPairsFound}")
            // Update the GUI which displays how many pairs of cards have been found.
            textViewNumPairs.text = "Pairs: ${memoryGame.numPairsFound} / ${boardSize.getNumPairs()}"
            if (memoryGame.haveWon()) {
                Snackbar.make(clRoot, "You won! Congratulations", Snackbar.LENGTH_LONG).show()
            }
        }
        textViewNumMoves.text = "Moves: ${memoryGame.getNumMoves()}";
        adapter.notifyDataSetChanged() // This notifies the recycler view to update because data on screen has changed
    }
}