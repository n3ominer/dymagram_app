package com.example.dymagram.views.recycler_view_adapters.home_adapters

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dymagram.R
import com.example.dymagram.data.model.posts.Post
import com.example.dymagram.views.viewholders.home_vh.PostsRvViewHolder

class PostsRvAdapter(val posts: List<Post>): RecyclerView.Adapter<PostsRvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsRvViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_posts_view_holder, parent, false)

        return PostsRvViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: PostsRvViewHolder, position: Int) {
        val postData = this.posts[position]

        //holder.postUserInfoCardView.setRenderEffect(RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.DECAL))
        // Binder le VH et le model
        Glide
            .with(holder.itemView)
            .load("https://robohash.org/${postData.username}")
            .into(holder.postImageView);

        Glide
            .with(holder.itemView)
            .load("https://robohash.org/${postData.user_id}")
            .into(holder.postUserProfilePicImageView);

        holder.postLikesTextView.text = postData.likes.toString()
        holder.postCommentsTextView.text = (postData.comments?.size ?: 0).toString()
        holder.postSharesTextView.text = postData.shares.toString()
        holder.postUserNameTextView.text = postData.username
        holder.postUserPseudoTextView.text = postData.user_id
        holder.postCommentTextView.text = postData.caption

        holder.postLikesImageView.setOnClickListener {
            Log.d("Post like button", "Post liked")
        }
        holder.postCommentsImageView.setOnClickListener {
            Log.d("Post like button", "Post commented")
        }
        holder.postSharesImageView.setOnClickListener {
            Log.d("Post like button", "Post shared")
        }
    }
}