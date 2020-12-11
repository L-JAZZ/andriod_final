package com.letsbuildthatapp.kotlinmessenger.messages

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.letsbuildthatapp.kotlinmessenger.MeetTypeActivity
import com.letsbuildthatapp.kotlinmessenger.R
import com.letsbuildthatapp.kotlinmessenger.UserProfileActivity
import com.letsbuildthatapp.kotlinmessenger.models.ChatMessage
import com.letsbuildthatapp.kotlinmessenger.models.User
import com.letsbuildthatapp.kotlinmessenger.registerlogin.RegisterActivity
import com.letsbuildthatapp.kotlinmessenger.views.ChatFromItem
import com.letsbuildthatapp.kotlinmessenger.views.ChatToItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.activity_chat_log.txtComment
import kotlinx.android.synthetic.main.activity_user_profile.*

class ChatLogActivity : AppCompatActivity() {

  companion object {
    val TAG = "ChatLog"
  }

  val adapter = GroupAdapter<ViewHolder>()


  var toUser: User? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_chat_log)
    recyclerview_chat_log.adapter = adapter
    toUser = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
    supportActionBar?.title = toUser?.username




    listenForMessages()

    send_button_chat_log.setOnClickListener {
      Log.d(TAG, "Attempt to send message....")
      performSendMessage()
    }

  }

  private fun listenForMessages() {
    val fromId = FirebaseAuth.getInstance().uid
    val toId = toUser?.uid
    val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")
    val intent = Intent(this, UserProfileActivity::class.java)
    ref.addChildEventListener(object: ChildEventListener {
      override fun onChildAdded(p0: DataSnapshot, p1: String?) {

        val chatMessage = p0.getValue(ChatMessage::class.java)

        if (chatMessage != null) {
          Log.d(TAG, chatMessage.text)

          //======================================
          //
          //=====================================

          if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            val currentUser = LatestMessagesActivity.currentUser ?: return
            adapter.add(ChatFromItem(chatMessage.text, currentUser,onClick = {
                intent.putExtra("USERNAME", currentUser.username)
                intent.putExtra("USERCITY", currentUser.city)
                intent.putExtra("USERPHONE", currentUser.phoneNumber)
                intent.putExtra("USERIMAGE", currentUser.profileImageUrl)
                intent.putExtra("USERID", currentUser.uid)

                startActivity(intent)
            }
                    ))
          } else {
            val currentUser = LatestMessagesActivity.currentUser ?: return
            adapter.add(ChatToItem(chatMessage.text, toUser!!, onClick = {
              intent.putExtra("USERNAME", toUser!!.username)
              intent.putExtra("USERCITY", toUser!!.city)
              intent.putExtra("USERPHONE", toUser!!.phoneNumber)
              intent.putExtra("USERIMAGE", toUser!!.profileImageUrl)
              intent.putExtra("USERID",  toUser!!.uid)
              intent.putExtra("CURRENTUSERNAME", currentUser.username)

              startActivity(intent)
            }))
          }

        }

        recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)

      }

      override fun onCancelled(p0: DatabaseError) {

      } 

      override fun onChildChanged(p0: DataSnapshot, p1: String?) {

      }

      override fun onChildMoved(p0: DataSnapshot, p1: String?) {

      }

      override fun onChildRemoved(p0: DataSnapshot) {

      }

    })

  }



  private fun performSendMessage() {
    // how do we actually send a message to firebase...
    val text = txtComment.text.toString()

    val fromId = FirebaseAuth.getInstance().uid
    val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
    val toId = user.uid

    if (fromId == null) return

//    val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
    val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()

    val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

    val chatMessage = ChatMessage(reference.key!!, text, fromId, toId)

    reference.setValue(chatMessage)
        .addOnSuccessListener {
          Log.d(TAG, "Saved our chat message: ${reference.key}")
          txtComment.text.clear()
          recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
        }

    toReference.setValue(chatMessage)

    val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
    latestMessageRef.setValue(chatMessage)

    val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")
    latestMessageToRef.setValue(chatMessage)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.toMeet -> {
        //syuda pishi
        toUser = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val intent = Intent(this, MeetTypeActivity::class.java)
        intent.putExtra("GUIDEID", toUser?.uid)
        startActivity(intent)
      }
    }

    return super.onOptionsItemSelected(item)
  }
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.chat_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

}
