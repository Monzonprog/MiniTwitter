package com.jmonzon.minitwitter.ui.dashboard.tweetList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import com.jmonzon.minitwitter.R

class BottonModalTweetFragment(private var idTweet: Int) : BottomSheetDialogFragment() {

    private lateinit var tweetListViewModel: TweetListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.botton_modal_tweet_fragment, container, false)


        val nav: NavigationView = view.findViewById(R.id.navigationViewBottonTweet)
        nav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.actionDeleteTweet -> {
                    tweetListViewModel.deleteTweet(idTweet)
                    this.dismiss()
                    true
                }
                else -> false
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tweetListViewModel = ViewModelProvider(this).get(TweetListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
