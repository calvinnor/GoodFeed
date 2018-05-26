package com.mpaani.goodfeed.core.event

import org.greenrobot.eventbus.EventBus

/**
 * Application-level Event posts & subscriptions.
 *
 * Internally uses GreenRobot's Events library.
 */
object Events {

    private var eventBus: EventBus = EventBus.getDefault()

    /**
     * Subscribe a class for events.
     */
    fun <T> subscribe(subscriber: T) = eventBus.register(subscriber)

    /**
     * Unsubscribe a previously subscribed class from events.
     */
    fun <T> unsubscribe(subscriber: T) = eventBus.unregister(subscriber)

    /**
     * Post an event to any listeners of type T
     */
    fun <T> post(event: T) = eventBus.post(event)

    /**
     * Post a sticky event to any listeners of type T.
     * Listeners who are not currently subscribed, but will subscribe in future, will get this event.
     */
    fun <T> postSticky(event: T) = eventBus.postSticky(event)

    /**
     * Remove a sticky event held by the Bus.
     */
    fun <T> removeSticky(event: T) = eventBus.removeStickyEvent(event)
}
