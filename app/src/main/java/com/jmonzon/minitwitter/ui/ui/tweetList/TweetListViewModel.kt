package com.jmonzon.minitwitter.ui.ui.tweetList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jmonzon.minitwitter.data.TweetRepository
import com.jmonzon.minitwitter.models.Tweet

class TweetListViewModel : ViewModel() {

    private var tweetRepository: TweetRepository = TweetRepository()
    private var tweets: LiveData<List<Tweet>> = tweetRepository.getAllTweets()

    fun getTweets(): LiveData<List<Tweet>>{ return tweets }
}