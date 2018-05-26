package com.mpaani.goodfeed.core.db.dao

import android.arch.persistence.room.*
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.db.POST_ID
import com.mpaani.goodfeed.core.db.POST_TABLE_NAME

@Dao
interface PostDao {

    @Query("SELECT * from $POST_TABLE_NAME")
    fun getPosts(): List<Post>

    @Query("SELECT * from $POST_TABLE_NAME WHERE $POST_ID = :postId")
    fun getPostForId(postId: Int): Post

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(vararg posts: Post)

    @Update
    fun updatePost(post: Post)

    @Delete
    fun deletePost(post: Post)
}
