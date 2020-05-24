package com.jmonzon.minitwitter.ui.dashboard.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jmonzon.minitwitter.data.ProfileRepository
import com.jmonzon.minitwitter.models.RequestUserProfile
import com.jmonzon.minitwitter.models.ResponseUserProfile

class ProfileViewModel : ViewModel() {

    companion object {
        private val profileRepository: ProfileRepository = ProfileRepository()
        var profile: LiveData<ResponseUserProfile> = profileRepository.getProfile()
    }

    fun getProfile(): LiveData<ResponseUserProfile>{
        return profile
    }

    fun updateProfile(requestUserProfile: RequestUserProfile) {
        profileRepository.updateProfile(requestUserProfile)
    }

    fun uploadPhoto (photo: String){
        profileRepository.uploadPhoto(photo)
    }
}