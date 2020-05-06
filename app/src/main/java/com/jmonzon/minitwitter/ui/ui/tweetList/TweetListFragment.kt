package com.jmonzon.minitwitter.ui.ui.tweetList


import android.content.Context
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jmonzon.minitwitter.R
import com.jmonzon.minitwitter.adapters.MyTweetRecyclerViewAdapter
import com.jmonzon.minitwitter.models.Tweet


class TweetListFragment : Fragment() {

    private var columnCount = 1
    private lateinit var tweetListViewModel: TweetListViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView


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

        recyclerView = view.findViewById(R.id.list)
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout)
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorAzul))
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            loadNewTweets(view.context)
        }

        loadTweets(view.context)
        return view
    }

    private fun loadTweets(context: Context) {
        tweetListViewModel.getTweets()
            .observe(viewLifecycleOwner, Observer {
                recyclerView.adapter = MyTweetRecyclerViewAdapter(it, context)
            })
    }

    private fun loadNewTweets(context: Context) {
        tweetListViewModel.getNewTweets()
            .observe(viewLifecycleOwner, Observer {
                recyclerView.adapter = MyTweetRecyclerViewAdapter(it, context)
                swipeRefreshLayout.isRefreshing = false
            })
        tweetListViewModel.getNewTweets().removeObservers(this)
    }
}