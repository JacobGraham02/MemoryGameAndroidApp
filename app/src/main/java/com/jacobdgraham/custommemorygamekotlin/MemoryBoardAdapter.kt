package com.jacobdgraham.custommemorygamekotlin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jacobdgraham.custommemorygamekotlin.models.BoardSize
import kotlin.math.min

// ViewHolder allows access to all the views inside of one RecyclerView parent element. In our game, this allows access to one generic memory card.
class MemoryBoardAdapter(
    private val context: Context,
    private val boardSize: BoardSize,
    private val cardImages: List<Int> // The int represents the location of one of the drawable images
) :
    RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {

    // In Kotlin, companion objects are singletons (single instance of itself) where we define constants
    companion object {
        private const val MARGIN_SIZE = 10
        private const val TAG = "MemoryBoardAdapter"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageButton = itemView.findViewById<ImageButton>(R.id.btnImage);

        fun bind(position: Int) {
            imageButton.setImageResource(cardImages[position]);
            imageButton.setOnClickListener{
                Log.i(TAG, "Clicked on position $position") // Logging information at the info level
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
