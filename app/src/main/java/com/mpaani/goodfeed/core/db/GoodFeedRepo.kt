package com.mpaani.goodfeed.core.db

import com.mpaani.goodfeed.core.data.model.Comment
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User
import com.mpaani.goodfeed.core.event.Events
import com.mpaani.goodfeed.feed.event.PostsEvent
import com.mpaani.goodfeed.feed.event.UsersEvent
import com.mpaani.goodfeed.post.event.CommentsEvent
import com.mpaani.goodfeed.post.event.PostEvent
import com.mpaani.goodfeed.post.event.UserEvent

/**
 * A repository class for holding Task information.
 *
 * Clients must never use this class directly.
 * Instead, communicate via the DataProxy interface.
 */
object GoodFeedRepo : DataProxy {

    private val userDao = GoodFeedDatabase.userDao()
    private val postDao = GoodFeedDatabase.postDao()
    private val commentsDao = GoodFeedDatabase.commentDao()

    private val dbThread = GoodFeedDatabase.dbThread

    override fun insertUsers(usersList: List<User>) {
        runOnDbThread { userDao.insertUsers(*usersList.toTypedArray()) }
    }

    override fun insertPosts(postsList: List<Post>) {
        runOnDbThread { postDao.insertPosts(*postsList.toTypedArray()) }
    }

    override fun insertComments(commentsList: List<Comment>) {
        runOnDbThread { commentsDao.insertComments(*commentsList.toTypedArray()) }
    }

    override fun getUsers() {
        runOnDbThread { Events.postSticky(UsersEvent(userDao.getUsers())) }
    }

    override fun getPosts() {
        runOnDbThread { Events.postSticky(PostsEvent(postDao.getPosts())) }
    }

    override fun getComments() {
        runOnDbThread { Events.postSticky(CommentsEvent(commentsDao.getComments())) }
    }

    override fun getPostById(postId: Int) {
        runOnDbThread { Events.postSticky(PostEvent(postDao.getPostForId(postId))) }
    }

    override fun getUserByEmail(userEmail: String) {
        runOnDbThread { Events.postSticky(UserEvent(userDao.getUserForEmail(userEmail))) }
    }

    private inline fun runOnDbThread(crossinline task: () -> Unit) {
        dbThread.post(Runnable { task() })
    }
}
