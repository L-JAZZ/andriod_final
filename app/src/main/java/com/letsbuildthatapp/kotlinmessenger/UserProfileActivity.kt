package com.letsbuildthatapp.kotlinmessenger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.letsbuildthatapp.kotlinmessenger.messages.LatestMessagesActivity
import com.letsbuildthatapp.kotlinmessenger.models.Comment
import com.letsbuildthatapp.kotlinmessenger.models.User
import com.letsbuildthatapp.kotlinmessenger.views.CommentAdapter
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_user_profile.*


class UserProfileActivity : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }

    companion object{
        const val CURRENTUSERNAME = "CURRENTUSERNAME"
       const val USERNAME = "USERNAME"
        const val USERCITY = "USERCITY"
        const val USERPHONE = "USERPHONE"
        const val USERIMAGE = "USERIMAGE"
        const val USERID = "USERID"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        currentUserProfile()
        addCommentToDatabase()
//        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid").child("commentList").push()



/*
        showComments(comm)
*/
        fetchUsers()
    }

    private fun currentUserProfile() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val userName = intent.getStringExtra(USERNAME)
                val userCity = intent.getStringExtra(USERCITY)
                val userPhone = intent.getStringExtra(USERPHONE)
                val userImage = intent.getStringExtra(USERIMAGE)

                if (userName.isNullOrBlank() && userCity.isNullOrBlank()
                        && userPhone.isNullOrBlank() && userImage.isNullOrBlank())
                {
                    LatestMessagesActivity.currentUser = p0.getValue(User::class.java)
                    txtProfileUserName.text = LatestMessagesActivity.currentUser?.username
                    txtProfileUserCity.text = LatestMessagesActivity.currentUser?.city
                    txtProfileUserPhoneNumber.text = LatestMessagesActivity.currentUser?.phoneNumber
                    Picasso.get().load(LatestMessagesActivity.currentUser?.profileImageUrl)
                            .into(imageProfileImage)
                }

                else{
                    txtProfileUserName.text = userName
                    txtProfileUserCity.text = userCity
                    txtProfileUserPhoneNumber.text = userPhone
                    Picasso.get().load(userImage)
                            .into(imageProfileImage)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }



        })
    }

        private fun addCommentToDatabase(){
           btnSendMComment.setOnClickListener {
               val uid = intent.getStringExtra(USERID)
               val userName = intent.getStringExtra(CURRENTUSERNAME)
               val ref = FirebaseDatabase.getInstance().getReference("/users/$uid").child("commentList").push()
               val comment = Comment(auth.currentUser!!.uid, uid,  txtComment.text.toString(), userName)
               Toast.makeText(this,txtComment.text.toString(), Toast.LENGTH_LONG).show()
               ref.setValue(comment)
               val intent = intent
               finish()
               startActivity(intent)
           }
        }



    private fun fetchUsers() {
        val uid = intent.getStringExtra(USERID)

        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid/commentList")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                p0.children.forEach {
                        val comments = it.getValue(Comment::class.java)
                        adapter.add(CommentAdapter(comments!!))
                    list_comments.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
                    list_comments.adapter = adapter
                }


            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}