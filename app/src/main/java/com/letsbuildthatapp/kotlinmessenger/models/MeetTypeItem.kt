package com.letsbuildthatapp.kotlinmessenger.models

data class MeetTypeItem(
        val uid: String,
        val touristId: String,
        val default: String,
        val transport: String,
        val hotel: String,
        val meal: String,
        val additional: String
) {constructor() : this("", "", "", "","", "", "") }