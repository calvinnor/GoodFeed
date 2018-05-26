package com.mpaani.goodfeed.post.event

import com.mpaani.goodfeed.core.data.model.User

/**
 * Event to be posted when a User is loaded from DB.
 */
data class UserEvent(val user: User?)
