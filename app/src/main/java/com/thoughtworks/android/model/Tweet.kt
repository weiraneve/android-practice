package com.thoughtworks.android.model

import com.google.gson.annotations.SerializedName

data class Tweet(
    var content: String? = null,
    var sender: Sender? = null,
    var images: List<Image>? = null,
    var comments: List<Comment>? = null,
    var error: String? = null,

    @SerializedName("unknown error")
    var unknownError: String? = null
)