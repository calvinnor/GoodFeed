package com.mpaani.goodfeed.feed.event

import com.mpaani.goodfeed.core.data.model.User

/**
 * Event to be posted when a list of Users are loaded from DB.
 */
data class UsersEvent(val userList: List<User>)
