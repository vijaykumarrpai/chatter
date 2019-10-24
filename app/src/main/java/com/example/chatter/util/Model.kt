package com.example.chatter.util

import android.net.Uri

data class User (
    val email:String? = "",
    val username:String?="",
    val imageUrl:String?="",
    val followHashtags:ArrayList<String>?= arrayListOf(),
    val followUsers:ArrayList<String>?= arrayListOf()
    )