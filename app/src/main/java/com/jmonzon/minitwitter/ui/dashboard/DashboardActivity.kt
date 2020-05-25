package com.jmonzon.minitwitter.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jmonzon.minitwitter.R
import com.jmonzon.minitwitter.common.Constants
import com.jmonzon.minitwitter.common.MyApp
import com.jmonzon.minitwitter.common.SharedPreferencesManager
import com.jmonzon.minitwitter.ui.dashboard.tweetList.NewTweetDialogFragment
import de.hdodenhof.circleimageview.CircleImageView

class DashboardActivity : AppCompatActivity() {

    private lateinit var fab : FloatingActionButton
    private lateinit var ivAvatar : CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        findViews()
        setImageAvatar()

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_tweet_like, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        fab.setOnClickListener(View.OnClickListener {
            val dialogFragment : NewTweetDialogFragment =
                NewTweetDialogFragment()
            dialogFragment.show(supportFragmentManager, "NewTweetDialogFragment")
        })
    }

    private fun findViews() {
        fab = findViewById(R.id.fab)
        ivAvatar = findViewById(R.id.imageViewToolbarPhoto)
    }

    private fun setImageAvatar() {
        val photoUrl: String =
            SharedPreferencesManager().getStringValueSharedPreferences(Constants.photoUrl)

        if (photoUrl != "") {
            Glide.with(this)
                .load(Constants.baseUrlPhotos + photoUrl)
                .dontAnimate() //Not recommended to use with CircleImageView
                .diskCacheStrategy(DiskCacheStrategy.NONE) //Don`t use cache
                .skipMemoryCache(true)
                .centerCrop()
                .into(ivAvatar)
        }
    }
}
