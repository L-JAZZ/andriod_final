package com.letsbuildthatapp.kotlinmessenger.views

import com.letsbuildthatapp.kotlinmessenger.R
import com.letsbuildthatapp.kotlinmessenger.models.Comment
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.comment_adapter.view.*

class CommentAdapter(val comment:Comment): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txtCommentUsername.text = comment.authorUserName
        viewHolder.itemView.txtCommentContent.text = comment.comment

    }
    override fun getLayout(): Int {
        return R.layout.comment_adapter
    }


}
