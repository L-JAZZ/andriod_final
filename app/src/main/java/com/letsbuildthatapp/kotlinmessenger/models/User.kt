package com.letsbuildthatapp.kotlinmessenger.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class User(val uid: String,
           val username: String,
           val profileImageUrl: String,
           val city: String,
           val phoneNumber: String,
           val commentsList: @RawValue List<Comment>
): Parcelable {
  constructor() : this("", "", "", "", "", emptyList())
}