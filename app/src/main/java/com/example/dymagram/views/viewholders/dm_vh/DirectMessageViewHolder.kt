package com.example.dymagram.views.viewholders.dm_vh

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dymagram.R

class DirectMessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val userProfilePicImageView: ImageView = itemView.findViewById(R.id.direct_message_user_profile_pic_image_view)
    val userLastMessageTextView: TextView = itemView.findViewById(R.id.conversation_last_message_text_view)
    val userMessagesCount: TextView = itemView.findViewById(R.id.conversation_messages_count_text_view)
}