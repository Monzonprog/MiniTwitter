package com.jmonzon.minitwitter.common

class Constants {
    companion object {
        //URL´s
       const val baseUrl: String = "https://www.minitwitter.com:3001/apiv1/"
       const val baseUrlPhotos: String = "https://www.minitwitter.com/apiv1/uploads/photos/"

        //Preferences
       const val tokenValue : String = "TOKEN_VALUE"
       const val userName : String = "USERNAME_VALUE"
       const val photoUrl : String = "PHOTOURL_VALUE"
       const val email : String = "EMAIL_VALUE"
       const val active : String = "ACTIVE_VALUE"
       const val created : String = "CREATED_VALUE"

        //StartActivityForResult
        const val SELECT_PHOTO_GALLERY: Int = 1

    }
}