package com.letsbuildthatapp.kotlinmessenger.messages

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.letsbuildthatapp.kotlinmessenger.R
import com.letsbuildthatapp.kotlinmessenger.UserProfileActivity
import com.letsbuildthatapp.kotlinmessenger.search.GoActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toMessages()
        toGo()
        toMeet()
        toProfile()
    }
    private fun toMessages(){
        btn_Messages.setOnClickListener {
            startActivity(Intent(this,LatestMessagesActivity::class.java))
        }
    }
    private fun toGo(){
        btnGo.setOnClickListener {
            startActivity(Intent(this,GoActivity::class.java))
        }
    }
    private fun toMeet(){
        button.setOnClickListener {
            startActivity(Intent(this,NewMessageActivity::class.java))
        }
    }
    private fun toProfile(){
        button4.setOnClickListener {
            startActivity(Intent(this,UserProfileActivity::class.java))
        }
    }
}