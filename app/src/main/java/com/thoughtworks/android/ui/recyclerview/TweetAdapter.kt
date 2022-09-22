package com.thoughtworks.android.ui.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.android.R
import com.thoughtworks.android.model.Tweet

class TweetAdapter(private val context: Context) : RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {

    private val tweets = ArrayList<Tweet>()

    fun setData(tweets : List<Tweet>?) {
        this.tweets.clear()
        this.tweets.addAll(tweets!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val from = LayoutInflater.from(context)
        return TweetViewHolder(from.inflate(R.layout.tweet_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        val tweet: Tweet = tweets[position]
        holder.name.text = ""
        holder.content.text = ""
        if (tweet.sender == null) {
            return
        }
        tweet.sender?.let {
            holder.name.text = it.nick
        }
        holder.content.text = tweet.content
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.recy_name)
        val content: TextView = itemView.findViewById(R.id.recy_content)
    }

}