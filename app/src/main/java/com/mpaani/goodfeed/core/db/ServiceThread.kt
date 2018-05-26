package com.mpaani.goodfeed.core.db

import android.os.Handler
import android.os.HandlerThread
import java.util.*

/**
 * A custom HandlerThread to service runnables.
 */
class ServiceThread(threadName: String) : HandlerThread(threadName) {

    private var workerThread: Handler? = null

    // Temp queue to maintain tasks before this DB Thread is started
    private var pendingTasks = PriorityQueue<Runnable>()

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        workerThread = Handler(looper)

        for (runnable in pendingTasks) {
            post(runnable)
        }

        // We don't need this queue anymore
        pendingTasks.clear()
    }

    fun post(task: Runnable) {
        if (workerThread == null) { // Save the task to a Queue
            pendingTasks.add(task)
            return
        }
        workerThread?.post(task)
    }
}
