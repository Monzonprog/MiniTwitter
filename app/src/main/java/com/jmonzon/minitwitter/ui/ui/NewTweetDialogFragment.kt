package com.jmonzon.minitwitter.ui.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.jmonzon.minitwitter.R
import com.jmonzon.minitwitter.common.Constants
import com.jmonzon.minitwitter.common.MyApp
import com.jmonzon.minitwitter.common.SharedPreferencesManager
import com.jmonzon.minitwitter.ui.ui.tweetList.TweetListViewModel

class NewTweetDialogFragment : DialogFragment() {

    private lateinit var createTweetButton: Button
    private lateinit var ivClose: ImageView
    private lateinit var ivAvatar: ImageView
    private lateinit var tvMessage: TextView
    private lateinit var dialogNewTweet: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullscreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(R.layout.fragment_new_tweet_full_dialog, container, false)
        dialogNewTweet = this.dialog!!
        findViews(view)
        createEvents()
        setImageAvatar()

        return view
    }

    private fun setImageAvatar() {
        val photoUrl: String =
            SharedPreferencesManager().getStringValueSharedPreferences(Constants.photoUrl)

        if (!photoUrl.isEmpty()) {
            Glide.with(MyApp.getContext())
                .load(Constants.baseUrlPhotos + photoUrl)
                .into(ivAvatar)
        }
    }

    private fun findViews(view: View) {
        createTweetButton = view.findViewById(R.id.buttonTwittear)
        ivClose = view.findViewById(R.id.imageViewClose)
        ivAvatar = view.findViewById(R.id.imageViewAvatar)
        tvMessage = view.findViewById(R.id.editTextMessage)
    }

    private fun createEvents() {
        createTweetButton.setOnClickListener(listener)
        ivClose.setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.imageViewClose -> {
                val message = tvMessage.text.toString()
                if (message.isNotEmpty()) {
                    showDialogConfirm()
                } else {
                    dialogNewTweet.dismiss()
                }
            }
            R.id.buttonTwittear -> {
                val message = tvMessage.text.toString()
                if (message.isEmpty()) {
                    Toast.makeText(context, "Debes escribir un mensaje", Toast.LENGTH_SHORT).show()
                } else {
                    val tweetListViewModel: TweetListViewModel =
                        ViewModelProvider(this).get(TweetListViewModel::class.java)
                    tweetListViewModel.insertNewTweet(message)
                    dialogNewTweet.dismiss()
                }
            }
        }
    }

    private fun showDialogConfirm() {
        //https://developer.android.com/guide/topics/ui/dialogs?hl=es-419#kotlin
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder?.setMessage(R.string.message_cancel_tweet)
            ?.setTitle(R.string.cancel_tweet)

        builder?.apply {
            setPositiveButton(R.string.remove_tweet,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                    dialogNewTweet.dismiss()
                })
            setNegativeButton(R.string.cancel_option,
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        }

        builder?.create()?.show()
    }
}