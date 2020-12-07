package com.letsbuildthatapp.kotlinmessenger.models

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue.TIMESTAMP
import java.sql.Timestamp
import java.time.LocalDateTime

class ChatMessage(val id: String, val text: String, val fromId: String, val toId: String) {

  constructor() : this("", "", "", ""  )
}