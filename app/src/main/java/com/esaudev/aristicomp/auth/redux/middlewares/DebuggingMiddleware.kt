package com.esaudev.aristicomp.auth.redux.middlewares

import android.util.Log
import com.esaudev.aristicomp.auth.redux.framework.Action
import com.esaudev.aristicomp.auth.redux.framework.Middleware
import com.esaudev.aristicomp.auth.redux.framework.State
import com.esaudev.aristicomp.auth.redux.framework.Store

/**
 * This [Middleware] is responsible for logging every [Action] that is processed to the Logcat, so
 * that we can use this for debugging.
 */
class DebuggingMiddleware<S: State, A: Action> : Middleware<S, A> {
    override suspend fun process(action: A, currentState: S, store: Store<S, A>) {
        Log.v(
            "LoggingMiddleware",
            "Processing action: $action; Current state: $currentState"
        )
    }
}