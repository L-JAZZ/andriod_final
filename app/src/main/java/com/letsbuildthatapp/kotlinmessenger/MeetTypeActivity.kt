package com.letsbuildthatapp.kotlinmessenger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.letsbuildthatapp.kotlinmessenger.models.MeetTypeItem
import com.letsbuildthatapp.kotlinmessenger.models.User
import kotlinx.android.synthetic.main.activity_meet_type.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class MeetTypeActivity : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }

    companion object{
        const val GUIDEID = "GUIDEID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meet_type)
        addMeetType()
    }

    private fun addMeetType(){
        val uid = intent.getStringExtra(GUIDEID)
        Toast.makeText(this,uid, Toast.LENGTH_LONG).show()
        val ref = FirebaseDatabase.getInstance().getReference("/meetType/$uid").push()
        send_meet_type.setOnClickListener {
            if (checkbox_defaultMeeting.isChecked && checkbox_bookTicket.isChecked && checkbox_bookHotel.isChecked &&
                    checkbox_bookRestraunts.isChecked && checkbox_additional.isChecked){
                val meetTypeItem = MeetTypeItem(uid, auth.currentUser!!.uid, "Default", "Need transport", "Need Hotel",
                        "Need restaurant", "Has additional orders")
                ref.setValue(meetTypeItem)
            }

            else if (checkbox_defaultMeeting.isChecked && !checkbox_bookTicket.isChecked && checkbox_bookHotel.isChecked &&
                    checkbox_bookRestraunts.isChecked && checkbox_additional.isChecked){
                val meetTypeItem = MeetTypeItem(uid, auth.currentUser!!.uid, "Default", "Doesn't need transport", "Need Hotel",
                        "Need restaurant", "Has additional orders")
                ref.setValue(meetTypeItem)
            }

            else if (checkbox_defaultMeeting.isChecked && checkbox_bookTicket.isChecked && !checkbox_bookHotel.isChecked &&
                    checkbox_bookRestraunts.isChecked && checkbox_additional.isChecked){
                val meetTypeItem = MeetTypeItem(uid, auth.currentUser!!.uid, "Default", "Doesn't need transport", "Doesn't eed Hotel",
                        "Need restaurant", "Has additional orders")
                ref.setValue(meetTypeItem)
            }

            else if (checkbox_defaultMeeting.isChecked && checkbox_bookTicket.isChecked && checkbox_bookHotel.isChecked &&
                    !checkbox_bookRestraunts.isChecked && checkbox_additional.isChecked){
                val meetTypeItem = MeetTypeItem(uid, auth.currentUser!!.uid, "Default", "Doesn't need transport", "Doesn't eed Hotel",
                        "Doesn't need restaurant", "Has additional orders")
                ref.setValue(meetTypeItem)
            }

            else if (checkbox_defaultMeeting.isChecked && checkbox_bookTicket.isChecked && checkbox_bookHotel.isChecked &&
                    checkbox_bookRestraunts.isChecked && !checkbox_additional.isChecked){
                val meetTypeItem = MeetTypeItem(uid, auth.currentUser!!.uid, "Default", "Doesn't need transport", "Doesn't eed Hotel",
                        "Doesn't need restaurant", "Doesn't have additional orders")
                ref.setValue(meetTypeItem)
            }
        }

    }
}
