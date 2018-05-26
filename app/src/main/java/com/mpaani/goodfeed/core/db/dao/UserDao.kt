package com.mpaani.goodfeed.core.db.dao

import android.arch.persistence.room.*
import com.mpaani.goodfeed.core.data.model.User
import com.mpaani.goodfeed.core.db.USER_EMAIL
import com.mpaani.goodfeed.core.db.USER_TABLE_NAME

@Dao
interface UserDao {

    @Query("SELECT * from $USER_TABLE_NAME")
    fun getUsers(): List<User>

    @Query("SELECT * from $USER_TABLE_NAME WHERE $USER_EMAIL = :userEmail")
    fun getUserForEmail(userEmail: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg users: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}
