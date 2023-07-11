package com.thoughtworks.android.ui.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.thoughtworks.android.R
import com.thoughtworks.android.data.model.Tweet

class TweetAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _tweets = ArrayList<Tweet>()

    companion object {
        private const val VIEW_TYPE_TWEET = 0
        private const val VIEW_TYPE_BOTTOM = 1
        private const val VIEW_TYPE_NETWORK_ERROR = 2
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(tweets: List<Tweet>) {
        this._tweets.clear()
        this._tweets.addAll(tweets)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        return when (viewType) {
            VIEW_TYPE_BOTTOM -> BottomViewHolder(
                inflater.inflate(R.layout.tweet_bottom, parent, false)
            )

            else -> TweetViewHolder(inflater.inflate(R.layout.tweet_item_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_TWEET) {
            val tweetViewHolder = holder as TweetViewHolder
            val tweet: Tweet = _tweets[position]
            tweetViewHolder.name.text = ""
            tweetViewHolder.content.text = ""
            if (tweet.sender == null) {
                return
            }
            tweet.sender?.let {
                tweetViewHolder.name.text = it.nick
            }
            tweetViewHolder.content.text = tweet.content
            tweetViewHolder.avatar.load(tweet.sender!!.avatar) {
                placeholder(R.drawable.avatar)
                transformations(CircleCropTransformation())
                size(
                    width = context.resources.getDimensionPixelSize(R.dimen.avatar_width),
                    height = context.resources.getDimensionPixelSize(R.dimen.avatar_height)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return _tweets.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (_tweets.size == 0) return VIEW_TYPE_NETWORK_ERROR
        return if (position < _tweets.size) VIEW_TYPE_TWEET else VIEW_TYPE_BOTTOM
    }

    class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.avatar)
        val name: TextView = itemView.findViewById(R.id.recy_name)
        val content: TextView = itemView.findViewById(R.id.recy_content)
    }

    class BottomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}