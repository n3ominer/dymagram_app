package com.example.dymagram.views.viewholders.home_vh

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dymagram.R
import com.google.android.material.card.MaterialCardView

class PostsRvViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val postImageView: ImageView = itemView.findViewById(R.id.user_post_image_view)
    val postMoreButton = itemView.findViewById<MaterialCardView>(R.id.post_more_card_view)
    val postUserNameTextView = itemView.findViewById<TextView>(R.id.user_name_text_view)
    val postUserPseudoTextView = itemView.findViewById<TextView>(R.id.user_pseudo_text_view)

    val postLikesImageView = itemView.findViewById<ImageView>(R.id.post_like_image_view)
    val postLikesTextView = itemView.findViewById<TextView>(R.id.post_likes_count_text_view)

    val postCommentsImageView = itemView.findViewById<ImageView>(R.id.post_comments_image_view)
    val postCommentsTextView = itemView.findViewById<TextView>(R.id.post_comments_counts_text_view)

    val postSharesImageView = itemView.findViewById<ImageView>(R.id.post_shares_image_view)
    val postSharesTextView = itemView.findViewById<TextView>(R.id.post_shares_counts_text_view)

    val postCommentTextView = itemView.findViewById<TextView>(R.id.post_comment_text_view)
    val postUserProfilePicImageView: ImageView = itemView.findViewById(R.id.post_user_profile_ic_image_view)

    //val postUserInfoCardView: View = itemView.findViewById(R.id.test)
}