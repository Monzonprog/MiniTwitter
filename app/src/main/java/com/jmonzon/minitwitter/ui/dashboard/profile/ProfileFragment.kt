package com.jmonzon.minitwitter.ui.dashboard.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jmonzon.minitwitter.R
import com.jmonzon.minitwitter.common.Constants
import com.jmonzon.minitwitter.models.RequestUserProfile
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.CompositePermissionListener
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import de.hdodenhof.circleimageview.CircleImageView
import java.util.jar.Manifest

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var ivAvatar: CircleImageView
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etCurrentPassword: EditText
    private lateinit var etWebsite: EditText
    private lateinit var etDescription: EditText
    private lateinit var btSave: Button
    private lateinit var btChangePassword: Button
    private lateinit var allPermissionsListener: PermissionListener
    private var loadingData: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        //Instanciate FAB from dashboard activity
        val fab: FloatingActionButton = requireActivity().findViewById(R.id.fab)
        //Hide FAB in this screen
        fab.hide()
        findViews(view)
        createEvents()

        //Observe and wait response from API
        profileViewModel.getProfile().observe(viewLifecycleOwner, Observer {
            etUsername.setText(it.username)
            etEmail.setText(it.email)
            etWebsite.setText(it.website)
            etDescription.setText(it.descripcion)
            if (it.photoUrl != "") {
                Glide.with(this)
                    .load(Constants.baseUrlPhotos + it.photoUrl)
                    .dontAnimate() //Not recommended to use with CircleImageView
                    .diskCacheStrategy(DiskCacheStrategy.NONE) //Don`t use cache
                    .skipMemoryCache(true)
                    .into(ivAvatar)
            }
            if (!loadingData) {
                Toast.makeText(context, "Datos guardados correctamente", Toast.LENGTH_SHORT)
                    .show()
                btSave.isEnabled = true
            }
        })

        profileViewModel.getPhotoUrl().observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(Constants.baseUrlPhotos + it)
                .dontAnimate() //Not recommended to use with CircleImageView
                .diskCacheStrategy(DiskCacheStrategy.NONE) //Don`t use cache
                .skipMemoryCache(true)
                .into(ivAvatar)

        })

        return view
    }

    private fun findViews(view: View) {
        ivAvatar = view.findViewById(R.id.imageViewAvatar)
        etUsername = view.findViewById(R.id.editTextUserName)
        etEmail = view.findViewById(R.id.editTextEmail)
        etCurrentPassword = view.findViewById(R.id.editTextCurrentPassword)
        etWebsite = view.findViewById(R.id.editTextWebSite)
        etDescription = view.findViewById(R.id.editTextDescription)
        btSave = view.findViewById(R.id.buttonSave)
        btChangePassword = view.findViewById(R.id.buttonChangePassword)
    }

    private fun createEvents() {
        btSave.setOnClickListener(listener)
        btChangePassword.setOnClickListener(listener)
        ivAvatar.setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.buttonSave -> {
                when {
                    etUsername.text.toString().isEmpty() -> {
                        etUsername.error = "El nombre de usuario es necesario"
                    }
                    etEmail.text.toString().isEmpty() -> {
                        etEmail.error = "El email es necesario"
                    }
                    etCurrentPassword.text.toString().isEmpty() -> {
                        etCurrentPassword.error = "La contraseña es requerida"
                    }
                    else -> {
                        val requestUserProfile = RequestUserProfile(
                            etDescription.text.toString(),
                            etEmail.text.toString(),
                            etCurrentPassword.text.toString(),
                            etUsername.text.toString(),
                            etWebsite.text.toString()
                        )
                        profileViewModel.updateProfile(requestUserProfile)
                        Toast.makeText(
                            context,
                            "Enviando información al servidor",
                            Toast.LENGTH_SHORT
                        ).show()
                        btSave.isEnabled = false
                        loadingData = false
                    }
                }
            }
            R.id.buttonChangePassword -> {
                Toast.makeText(context, "Botón cambiar clave", Toast.LENGTH_SHORT).show()
            }
            R.id.imageViewAvatar -> {
                checkPermissions()
            }
        }
    }

    private fun checkPermissions() {
        val dialogOnDeniedPermissionListener: PermissionListener = DialogOnDeniedPermissionListener.Builder.withContext(activity)
            .withTitle("Permisos")
            .withMessage("Los permisos solicitados son necesarios para poder seleccionar una foto de perfil")
            .withButtonText("Aceptar")
            .withIcon(R.mipmap.ic_launcher)
            .build()

        allPermissionsListener = CompositePermissionListener(
                activity as PermissionListener,
                dialogOnDeniedPermissionListener)

        Dexter.withContext(activity)
            .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(allPermissionsListener)
            .check()
    }
}

