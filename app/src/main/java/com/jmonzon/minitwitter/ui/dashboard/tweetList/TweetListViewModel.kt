package com.jmonzon.minitwitter.ui.dashboard.tweetList

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jmonzon.minitwitter.data.TweetRepository
import com.jmonzon.minitwitter.models.Tweet
import com.jmonzon.minitwitter.ui.dashboard.BottonModalTweetFragment

class TweetListViewModel : ViewModel() {

    companion object {
        val tweetRepository: TweetRepository = TweetRepository()
        var tweets: LiveData<List<Tweet>> = tweetRepository.getAllTweets()

    }

    private var favTweets: LiveData<List<Tweet>> = MutableLiveData()

    fun getTweets(): LiveData<List<Tweet>> {
        return tweets
    }

    fun getFavTweets(): LiveData<List<Tweet>> {
        favTweets = tweetRepository.getFavsTweets()
        return favTweets
    }

    fun getNewFavTweets(): LiveData<List<Tweet>> {
        getNewTweets()
        return getFavTweets()
    }

    fun getNewTweets(): LiveData<List<Tweet>> {
        return tweetRepository.getAllTweets()
    }

    fun insertNewTweet(message: String) {
        tweetRepository.createTweet(message)
    }

    fun likeTweet(idTweet: Int) {
        tweetRepository.likeTweet(idTweet)
    }

    fun deleteTweet(idTweet: Int) {
        tweetRepository.deleteTweet(idTweet)
    }

    fun openDialogTweetMenu(context: Context, idTweet: Int){
        val bottonModalTweetFragment = BottonModalTweetFragment(idTweet)
        bottonModalTweetFragment.show((context as AppCompatActivity).supportFragmentManager, "BottonModalTweetFragment")
    }
}