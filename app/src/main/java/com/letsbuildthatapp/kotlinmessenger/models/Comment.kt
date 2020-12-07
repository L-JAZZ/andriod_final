package com.letsbuildthatapp.kotlinmessenger.models

data class Comment(
        val authorId: String,
        val receiverId:String,
        val comment:String,
        val authorUserName:String
) {
    constructor() : this("", "", "", "")
}