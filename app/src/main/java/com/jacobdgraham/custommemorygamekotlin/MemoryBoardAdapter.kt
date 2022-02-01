package com.jacobdgraham.custommemorygamekotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

// ViewHolder allows access to all the views inside of one RecyclerView parent element. In our game, this allows access to one generic memory card.
class MemoryBoardAdapter(private val context: Context, private val numPieces: Int) :
    RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {

        }
    }
    // LayoutInflater is used to create a View (or Layout) object from a preexisting XML file
    // findViewById gives you a reference to a view that has already been created.
    // Logic behind creating a single view of our recycler view will be contained in this function
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Hard code the initial card width and height values to have 2 cards horizontally and 4 cards vertically.
        val cardWidth = parent.width / 2;
        val cardHeight = parent.height / 4;
        val cardSideLength = min(cardWidth, cardHeight)

        val view = LayoutInflater.from(context).inflate(R.layout.memory_card, parent, false)

        val layoutParams = view.findViewById<CardView>(R.id.crdView).layoutParams
        layoutParams.width = cardSideLength
        layoutParams.height = cardSideLength

        return ViewHolder(view)
    }

    // Login behind taking the parameter defined by position: Int and binding it to the ViewHolder defined by parameter holder: ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position);
    }

    // Logic behind calculating how many items are in our RecyclerView
    override fun getItemCount() = numPieces;


}
