package com.jmonzon.minitwitter.adapters

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmonzon.minitwitter.R
import com.jmonzon.minitwitter.common.Constants
import com.jmonzon.minitwitter.common.SharedPreferencesManager
import com.jmonzon.minitwitter.models.Tweet
import com.jmonzon.minitwitter.ui.dashboard.tweetList.TweetListViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_tweet.view.*

class MyTweetRecyclerViewAdapter(
    private var mValues: List<Tweet>,
    private var context: Context
) : RecyclerView.Adapter<MyTweetRecyclerViewAdapter.ViewHolder>() {

    private var userName: String =
        SharedPreferencesManager().getStringValueSharedPreferences(Constants.userName)

    init {
        Log.i("Adapter:", " - Iniciado")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tweet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.tvUsername.text = "@" + item.user.username
        holder.tvMessage.text = item.mensaje
        holder.tvLikeCount.text = item.likes.size.toString()
        //If user have photo we use it
        if (item.user.photoUrl != "") {
            Glide.with(context)
                .load(Constants.baseUrlPhotos + item.user.photoUrl)
                .into(holder.ivAvatar)
        }

        //reset color
        Glide.with(context)
            .load(R.drawable.ic_like)
            .into(holder.ivLike)
        holder.tvLikeCount.setTextColor(context.resources.getColor(android.R.color.black))
        holder.tvLikeCount.setTypeface(null, Typeface.BOLD)

        // Implement funcionality when press in like icon
        holder.ivLike.setOnClickListener {
            TweetListViewModel().likeTweet(item.id)
        }

        //It tweet have a like from this user we paint a pink heart
        for (like in item.likes) {
            if (like.username == userName) {
                Glide.with(context)
                    .load(R.drawable.ic_like_pink)
                    .into(holder.ivLike)
                holder.tvLikeCount.setTextColor(context.resources.getColor(R.color.pink))
                holder.tvLikeCount.setTypeface(null, Typeface.BOLD)
                break
            }
        }

        //If we writed the tweet this IV will be show for call to dialogTweetMenu
        holder.ivShowMenu.visibility = View.GONE
        if (item.user.username == userName) {
            holder.ivShowMenu.visibility = View.VISIBLE
            holder.ivShowMenu.setOnClickListener {
                TweetListViewModel().openDialogTweetMenu(it.context, item.id)
            }
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvUsername: TextView = mView.textViewUsername
        val tvMessage: TextView = mView.textViewMessage
        val tvLikeCount: TextView = mView.textViewLikesCount
        val ivAvatar: CircleImageView = mView.imageViewAvatar
        val ivLike: ImageView = mView.imageViewLike
        val ivShowMenu: ImageView = mView.imageViewShowMenu

        override fun toString(): String {
            return ""
        }
    }
}

