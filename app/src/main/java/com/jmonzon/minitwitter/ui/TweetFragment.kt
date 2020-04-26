package com.jmonzon.minitwitter.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmonzon.minitwitter.R
import com.jmonzon.minitwitter.adapters.MyTweetRecyclerViewAdapter
import com.jmonzon.minitwitter.models.Tweet
import com.jmonzon.minitwitter.retrofit.AuthMiniTwitterClient
import com.jmonzon.minitwitter.retrofit.AuthMiniTwitterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TweetFragment : Fragment() {

    private var columnCount = 1
    lateinit var authMiniTwitterService: AuthMiniTwitterService
    lateinit var authMiniTwitterClient: AuthMiniTwitterClient
    lateinit var tweetList: List<Tweet>
    lateinit var adapterTweet: MyTweetRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                retrofitInit()
                val call: Call<List<Tweet>> = authMiniTwitterService.getAllTweets()
                call.enqueue(object : Callback<List<Tweet>> {
                    override fun onResponse(
                        call: Call<List<Tweet>>,
                        response: Response<List<Tweet>>
                    ) {
                        if (response.isSuccessful) {
                            tweetList = response.body()!!
                            adapterTweet = MyTweetRecyclerViewAdapter(tweetList, context)
                            adapter = adapterTweet
                            Log.i("TweetFragment", "Se han recibido los tweets")
                        } else {
                            Toast.makeText(
                                context,
                                "Ha ocurrido al recuperar los tweets",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                        Toast.makeText(
                            context,
                            "Error en la conexión",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })

            }
        }
        return view
    }

    private fun retrofitInit() {
        authMiniTwitterClient = AuthMiniTwitterClient().getInstance()
        authMiniTwitterService = AuthMiniTwitterClient().authMiniTwitterService
    }

    private fun loadTweetData() {

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TweetFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
