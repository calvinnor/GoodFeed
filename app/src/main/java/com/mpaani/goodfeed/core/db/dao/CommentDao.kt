package com.mpaani.goodfeed.core.db.dao

import android.arch.persistence.room.*
import com.mpaani.goodfeed.core.data.model.Comment
import com.mpaani.goodfeed.core.db.COMMENT_POST_ID
import com.mpaani.goodfeed.core.db.COMMENT_TABLE_NAME

@Dao
interface CommentDao {

    @Query("SELECT * from $COMMENT_TABLE_NAME")
    fun getComments(): List<Comment>

    @Query("SELECT * from $COMMENT_TABLE_NAME WHERE $COMMENT_POST_ID = :postId")
    fun getCommentsForPost(postId: Int): List<Comment>

    @Query("SELECT COUNT(*) FROM $COMMENT_TABLE_NAME WHERE $COMMENT_POST_ID = :postId")
    fun getCommentCountForPost(postId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComments(vararg comment: Comment)

    @Update
    fun updateComment(comment: Comment)

    @Delete
    fun deleteComment(comment: Comment)
}
