package com.jmonzon.minitwitter.ui.dashboard.tweetList


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jmonzon.minitwitter.R
import com.jmonzon.minitwitter.adapters.MyTweetRecyclerViewAdapter


class TweetListFragment : Fragment() {

    private lateinit var tweetListViewModel: TweetListViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private var tweetListType: Int = 1

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

        //Instanciate FAB from dashboard activity
        val fab: FloatingActionButton = requireActivity().findViewById(R.id.fab)

        //Recover argument for know what option is selected
        arguments.let {
            //Without SafeArgs
            tweetListType = requireArguments().getInt("TWEET_LIST_TYPE")
            Log.i("TWEET_LIST_TYPE => ", tweetListType.toString())
        }

        recyclerView = view.findViewById(R.id.list)
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout)
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorAzul))
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            //Depending if we are in the list tweet or fav tweet swipeRefreshLayout reload
            //a list or other
            if (tweetListType == 1) {
                loadNewTweets(view.context)
            } else {
                loadNewFavTweets(view.context)
            }
        }

        if (tweetListType == 1) {
            //If we are in principal screen we show FAB for write a new tweet
            fab.show()
            loadTweets(view.context)
        } else {
            //If we are in fav tweets screen don´t show the FAB
            fab.hide()
            loadFavTweets(view.context)
        }
        return view
    }

    //Function for recoverty tweets from the list saved
    private fun loadTweets(context: Context) {
        tweetListViewModel.getTweets()
            .observe(viewLifecycleOwner, Observer {
                recyclerView.adapter = MyTweetRecyclerViewAdapter(it, context)
            })
    }

    //Function for recovery favorites tweets
    private fun loadFavTweets(context: Context) {
        tweetListViewModel.getFavTweets()
            .observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = MyTweetRecyclerViewAdapter(it, context)
        })
    }

    //Function for recovery favorites tweets from repository when use swipeRefresh
    private fun loadNewFavTweets(context: Context) {
        tweetListViewModel.getNewFavTweets()
            .observe(viewLifecycleOwner, Observer {
                recyclerView.adapter = MyTweetRecyclerViewAdapter(it, context)
                swipeRefreshLayout.isRefreshing = false
            })
        tweetListViewModel.getNewFavTweets().removeObservers(this)

    }

    //Function for recovery tweets from repository when use swipeRefresh
    private fun loadNewTweets(context: Context) {
        tweetListViewModel.getNewTweets()
            .observe(viewLifecycleOwner, Observer {
                recyclerView.adapter = MyTweetRecyclerViewAdapter(it, context)
                swipeRefreshLayout.isRefreshing = false
            })
        tweetListViewModel.getNewTweets().removeObservers(this)
    }
}