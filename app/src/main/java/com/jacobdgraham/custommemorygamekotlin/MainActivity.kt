package com.jacobdgraham.custommemorygamekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Continue video from 32:19 minutes: https://www.youtube.com/watch?v=C2DBDZKkLss&list=LL&index=9&t=671s

class MainActivity : AppCompatActivity() {
    // The lateinit keyword allows you to avoid initializing a property when an object is constructed.
    private lateinit var recyclerViewBoard: RecyclerView
    private lateinit var textViewNumPairs: TextView
    private lateinit var textViewNumMoves: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Look in the layout directory of project resources and select activity_main layout.
        // By default, the constraint layout is the root element for activity_main XML.
        // Layout weight is how much space the parent element should allocate for a child element

        // findViewById finds a view (element) in the content view activity_main.
        recyclerViewBoard = findViewById(R.id.recyclerViewBoard)
        textViewNumPairs = findViewById(R.id.txtViewNumPairs)
        textViewNumMoves = findViewById(R.id.txtViewNumMoves)
        // LayoutManager: Measures and positions item views.
        // Adapter: Providing a binding for the data set to the views of the RecyclerView.
        recyclerViewBoard.adapter = MemoryBoardAdapter(this, 8)
        recyclerViewBoard.setHasFixedSize(true); // RecyclerView can perform optimizations if it can know in advance that RecyclerView's size is not affected by the
        // adapter contents. RecyclerView size can still change depending on other factors like parent size, but the size calculation cannot depend on the size of
        // its children or contents of its adapter, and only the number of items in the adapter.
        // We are passing in this because the current class is a context.
        recyclerViewBoard.layoutManager = GridLayoutManager(this,2)
    }
}