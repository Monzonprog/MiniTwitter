package com.jmonzon.minitwitter.ui.dashboard.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jmonzon.minitwitter.R
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var ivAvatar: CircleImageView
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etCurrentPassword: EditText
    private lateinit var btSave: Button
    private lateinit var btChangePassword: Button

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



        return view
    }

    private fun findViews(view: View) {
        ivAvatar = view.findViewById(R.id.imageViewAvatar)
        etUsername = view.findViewById(R.id.editTextUserName)
        etEmail = view.findViewById(R.id.editTextEmail)
        etCurrentPassword = view.findViewById(R.id.editTextCurrentPassword)
        btSave = view.findViewById(R.id.buttonSave)
        btChangePassword = view.findViewById(R.id.buttonChangePassword)
    }

    private fun createEvents() {
        btSave.setOnClickListener(listener)
        btChangePassword.setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.buttonSave -> {
                Toast.makeText(context, "Botón save", Toast.LENGTH_SHORT).show()
            }
            R.id.buttonChangePassword -> {
                Toast.makeText(context, "Botón cambiar clave", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
