package com.mpaani.goodfeed.core.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.mpaani.goodfeed.core.data.model.Comment
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User
import com.mpaani.goodfeed.core.db.dao.CommentDao
import com.mpaani.goodfeed.core.db.dao.PostDao
import com.mpaani.goodfeed.core.db.dao.UserDao

@Database(entities = [(Comment::class), (User::class), (Post::class)], version = DATABASE_VERSION, exportSchema = false)
abstract class GoodFeedDatabase : RoomDatabase() {

    abstract fun commentDao(): CommentDao
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao

    companion object {

        private const val TAG = "GoodFeedDatabase"

        private lateinit var INSTANCE: GoodFeedDatabase
        lateinit var dbThread: ServiceThread

        /**
         * Methods for fetching DB operation objects.
         */
        fun commentDao() = INSTANCE.commentDao()
        fun userDao() = INSTANCE.userDao()
        fun postDao() = INSTANCE.postDao()

        /**
         * Initialise the Room database.
         */
        fun init(context: Context) {
            INSTANCE = getDatabase(context.applicationContext, GoodFeedDatabase::class.java, DATABASE_NAME)
                    .build()
            dbThread = ServiceThread(threadName = "DbThread")
            dbThread.start()
        }

        private fun <T : RoomDatabase> getDatabase(appContext: Context, klass: Class<T>, dbName: String): RoomDatabase.Builder<T> {
            return if (DATABASE_IN_MEMORY_TEST)
                Room.inMemoryDatabaseBuilder(appContext, klass)
            else
                Room.databaseBuilder(appContext, klass, dbName)
        }
    }
}
