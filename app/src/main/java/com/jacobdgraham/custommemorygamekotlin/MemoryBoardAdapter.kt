package com.jacobdgraham.custommemorygamekotlin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.jacobdgraham.custommemorygamekotlin.models.BoardSize
import com.jacobdgraham.custommemorygamekotlin.models.MemoryCard
import kotlin.math.min

// ViewHolder allows access to all the views inside of one RecyclerView parent element. In our game, this allows access to one generic memory card.
class MemoryBoardAdapter(
    private val context: Context,
    private val boardSize: BoardSize,
    private val cards: List<MemoryCard>, // The int represents the location of one of the drawable images
    private val cardClickListener: CardClickListener
) :
    RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {

    // In Kotlin, companion objects are singletons (single instance of itself) where we define constants
    companion object {
        private const val MARGIN_SIZE = 10
        private const val TAG = "MemoryBoardAdapter"
    }
    // Whoever constructs an instance of the MemoryBoardAdapter will be responsible for passing an instance of the interface
    interface CardClickListener {
        fun onCardClicked(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageButton = itemView.findViewById<ImageButton>(R.id.btnImage);

        fun bind(position: Int) {
            val memoryCard = cards[position]
            imageButton.setImageResource(if (cards[position].isFaceUp) {
                cards[position].identifier
            } else {
                R.drawable.ic_launcher_background
            })
            // alpha property refers to how visible the image button will be
            imageButton.alpha = if (memoryCard.isMatched) .4f else 1.0f


            val colourStateList = if (memoryCard.isMatched) ContextCompat.getColorStateList(context, R.color.color_gray) else null

            //This property of class ViewCompat is a way to set the background or shading on the image button
            ViewCompat.setBackgroundTintList(imageButton, colourStateList)

            // When this click listener triggers, we want MemoryBoardAdapter to notify the main activity so the main activity can tell the MemoryCard
            // class that the user has taken some action. The standard pattern for this is to use an interface.
            imageButton.setOnClickListener{
                Log.i(TAG, "Clicked on position $position") // Logging information at the info level
                cardClickListener.onCardClicked(position)
            }
        }
    }
    // LayoutInflater is used to create a View (or Layout) object from a preexisting XML file
    // findViewById gives you a reference to a view that has already been created.
    // Logic behind creating a single view of our recycler view will be contained in this function
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Hard code the initial card width and height values to have 2 cards horizontally and 4 cards vertically.
        val cardWidth = parent.width / boardSize.getWidth() - (2*MARGIN_SIZE)
        val cardHeight = parent.height / boardSize.getHeight() - (2*MARGIN_SIZE)
        val cardSideLength = min(cardWidth, cardHeight)

        val view = LayoutInflater.from(context).inflate(R.layout.memory_card, parent, false)

        val layoutParams = view.findViewById<CardView>(R.id.crdView).layoutParams as ViewGroup.MarginLayoutParams

        layoutParams.width = cardSideLength
        layoutParams.height = cardSideLength
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)

        return ViewHolder(view)
    }

    // Login behind taking the parameter defined by position: Int and binding it to the ViewHolder defined by parameter holder: ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    // Logic behind calculating how many items are in our RecyclerView
    override fun getItemCount() = boardSize.numCards


}
