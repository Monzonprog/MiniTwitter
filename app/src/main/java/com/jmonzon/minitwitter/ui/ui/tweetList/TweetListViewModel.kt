package com.jmonzon.minitwitter.ui.ui.tweetList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jmonzon.minitwitter.data.TweetRepository
import com.jmonzon.minitwitter.models.Tweet

class TweetListViewModel : ViewModel() {

    companion object {
        val  tweetRepository: TweetRepository = TweetRepository()
        var tweets: LiveData<List<Tweet>> =  tweetRepository.getAllTweets()
    }

    fun getTweets(): LiveData<List<Tweet>> {
        return tweets
    }

    fun insertNewTweet(message: String){
        tweetRepository.createTweet(message)
    }



}