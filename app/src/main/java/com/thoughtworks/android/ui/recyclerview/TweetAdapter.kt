package com.thoughtworks.android.ui.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.android.R
import com.thoughtworks.android.model.Tweet

class TweetAdapter : RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {

    private val tweets = ArrayList<Tweet>()

    @SuppressLint("NotifyDataSetChanged")
    fun setTweets(tweets : List<Tweet>) {
        this.tweets.clear()
        this.tweets.addAll(tweets)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val from = LayoutInflater.from(parent.context)
        return TweetViewHolder(from.inflate(R.layout.tweet_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.tvName.text = tweets[position].name
        holder.tvContent.text = tweets[position].content

    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvContent: TextView = itemView.findViewById(R.id.tv_content)
    }

}