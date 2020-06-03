package com.jmonzon.minitwitter.ui.dashboard.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jmonzon.minitwitter.data.ProfileRepository
import com.jmonzon.minitwitter.models.RequestUserProfile
import com.jmonzon.minitwitter.models.ResponseUserProfile
import java.io.File

class ProfileViewModel : ViewModel() {

    companion object {
        private val profileRepository: ProfileRepository = ProfileRepository()
        var profile: LiveData<ResponseUserProfile> = profileRepository.getProfile()
        var photoProfile: LiveData<String> = profileRepository.getphotoProfile()
    }

    fun getProfile(): LiveData<ResponseUserProfile>{
        return profile
    }

    fun getPhotoUrl(): LiveData<String>{
        return photoProfile
    }

    fun updateProfile(requestUserProfile: RequestUserProfile) {
        profileRepository.updateProfile(requestUserProfile)
    }

    fun uploadPhoto (photo: File){
        profileRepository.uploadPhoto(photo)
    }
}