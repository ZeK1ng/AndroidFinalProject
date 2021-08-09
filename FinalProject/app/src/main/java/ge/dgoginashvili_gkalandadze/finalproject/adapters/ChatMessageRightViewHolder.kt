package com.example.clientapp.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.dgoginashvili_gkalandadze.finalproject.R

class ChatMessageRightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var message: TextView
    var time: TextView

    init {
        message = itemView.findViewById(R.id.right_message)
        time = itemView.findViewById(R.id.right_time)
    }
}