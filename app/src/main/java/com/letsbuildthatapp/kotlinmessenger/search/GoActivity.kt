package com.letsbuildthatapp.kotlinmessenger.search

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.letsbuildthatapp.kotlinmessenger.R
import com.letsbuildthatapp.kotlinmessenger.messages.ChatLogActivity
import com.letsbuildthatapp.kotlinmessenger.messages.NewMessageActivity
import com.letsbuildthatapp.kotlinmessenger.messages.UserItem
import com.letsbuildthatapp.kotlinmessenger.models.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_go.*

class GoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go)
        search()
    }

    private fun search(){
        search_button.setOnClickListener {
            var city:String = search_city_textfield.text.toString()
            fetchUsers(city)
        }
    }


    private fun fetchUsers(city: String) {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach {
                    val user = it.getValue(User::class.java)
                    if (user != null && user.city.equals(city)) {
                        adapter.add(UserItem(user))
                    }
                }
                //////////////////////////////////////////////////to start messaging
                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem

                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    intent.putExtra(NewMessageActivity.USER_KEY, userItem.user)
                    startActivity(intent)
                    finish()
                }
                recycler_city.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL ,false)
                recycler_city.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}
