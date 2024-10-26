package com.example.dymagram.views.recycler_view_adapters.dm_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dymagram.R
import com.example.dymagram.data.model.messages.Message
import com.example.dymagram.views.viewholders.dm_vh.DirectMessageViewHolder

class DirectMessagesRvAdapter(var messages: List<Message>): RecyclerView.Adapter<DirectMessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectMessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.direct_message_rv_cell, parent, false)
        return DirectMessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: DirectMessageViewHolder, position: Int) {
        val messageData = this.messages[position]

        holder.userLastMessageTextView.text = messageData.messages.last().message
        holder.userMessagesCount.text = messageData.messages.size.toString()
        Glide
            .with(holder.itemView)
            .load("https://robohash.org/${messageData.convId}")
            .into(holder.userProfilePicImageView);
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}