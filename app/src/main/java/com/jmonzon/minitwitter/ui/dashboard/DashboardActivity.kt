package com.jmonzon.minitwitter.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import com.jmonzon.minitwitter.common.SharedPreferencesManager
import com.jmonzon.minitwitter.ui.dashboard.profile.ProfileViewModel
import com.jmonzon.minitwitter.ui.dashboard.tweetList.NewTweetDialogFragment
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import de.hdodenhof.circleimageview.CircleImageView
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class DashboardActivity : AppCompatActivity(), PermissionListener {

    private lateinit var fab: FloatingActionButton
    private lateinit var ivAvatar: CircleImageView
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
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


        fab.setOnClickListener {
            val dialogFragment =
                NewTweetDialogFragment()
            dialogFragment.show(supportFragmentManager, "NewTweetDialogFragment")
        }

        profileViewModel.getPhotoUrl().observe(this, Observer {
                Glide.with(this)
                    .load(Constants.baseUrlPhotos + it)
                    .dontAnimate() //Not recommended to use with CircleImageView
                    .diskCacheStrategy(DiskCacheStrategy.NONE) //Don`t use cache
                    .skipMemoryCache(true)
                    .into(ivAvatar)

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
                .into(ivAvatar)
        }
    }

    //Catch URI to image from gallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == Constants.SELECT_PHOTO_GALLERY) {
                if (data != null) {
                    val imagenSeleccionada: Uri = data.data!!
                    val parcelFileDescriptor =
                        this.contentResolver.openFileDescriptor(imagenSeleccionada, "r", null)

                    parcelFileDescriptor?.let {
                        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                        val file =
                            File(this.cacheDir, getFileName(imagenSeleccionada))
                        val outputStream = FileOutputStream(file)
                        IOUtils.copy(inputStream, outputStream)
                        profileViewModel.uploadPhoto(file)
                    }
                }
            }
        }
    }
    private fun getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = contentResolver.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        Log.e("Solicitud de permisos", "El usuario ha concedido permisos a la aplicación")
        val seleccionarFoto: Intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(seleccionarFoto, Constants.SELECT_PHOTO_GALLERY)

    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        Log.e("Solicitud de permisos", "El usuario ha rechazado dar permisos a la aplicación")
        Toast.makeText(this, "No se puede seleccionar ninguna fotografía", Toast.LENGTH_LONG)
            .show()
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {}
}
