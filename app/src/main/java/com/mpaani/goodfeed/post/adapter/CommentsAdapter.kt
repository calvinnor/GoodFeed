package com.mpaani.goodfeed.post.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.extension.loadAvatar
import com.mpaani.goodfeed.post.viewmodel.CommentViewModel
import kotlinx.android.synthetic.main.view_comment_item.view.*
import java.util.*

/**
 * This adapter shows the Comments of users on a given post.
 */
class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    private val commentList: MutableList<CommentViewModel> = ArrayList()

    fun setItems(commentList: List<CommentViewModel>) {
        this.commentList.apply {
            clear()
            addAll(commentList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_comment_item, parent, false))

    override fun getItemCount() = commentList.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) = holder.bind(commentList[position])

    class CommentViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView) {

        private val commentUserImage = rootView.comment_user_image
        private val commentAuthorName = rootView.comment_user_name
        private val commentBody = rootView.comment_body

        fun bind(commentItem: CommentViewModel) {
            commentUserImage.loadAvatar(commentItem.commentEmail)
            commentAuthorName.text = commentItem.commentName
            commentBody.text = commentItem.commentBody

            rootView.setOnClickListener {
                val isExpanded = commentBody.maxLines == Integer.MAX_VALUE
                if (isExpanded) commentBody.maxLines = rootView.resources.getInteger(R.integer.comment_body_max_lines)
                else commentBody.maxLines = Integer.MAX_VALUE
            }
        }
    }
}
