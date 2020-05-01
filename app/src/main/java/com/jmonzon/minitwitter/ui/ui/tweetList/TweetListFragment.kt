package com.jmonzon.minitwitter.ui.ui.tweetList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmonzon.minitwitter.R
import com.jmonzon.minitwitter.adapters.MyTweetRecyclerViewAdapter

class TweetListFragment : Fragment() {

    private var columnCount = 1
    private lateinit var tweetListViewModel: TweetListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tweetListViewModel =
            ViewModelProvider(this).get(TweetListViewModel::class.java)
        val view = inflater.inflate(
            R.layout.fragment_tweet_list,
            container,
            false
        )

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                tweetListViewModel.getTweets()
                    .observe(viewLifecycleOwner, Observer { tweetListFromRepository ->
                        adapter = MyTweetRecyclerViewAdapter(tweetListFromRepository, context)
                    })
            }
        }
        return view
    }
}