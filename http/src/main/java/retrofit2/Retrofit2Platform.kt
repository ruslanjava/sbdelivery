package retrofit2

import java.util.concurrent.Executor

object Retrofit2Platform {

    fun defaultCallAdapterFactories(executor: Executor?): List<CallAdapter.Factory> {
        var executor: Executor? = executor
        if (executor == null) {
            executor = defaultCallbackExecutor()
        }
        return Platform.get().defaultCallAdapterFactories(executor)
    }

    fun defaultCallbackExecutor(): Executor? {
        return Platform.get().defaultCallbackExecutor()
    }

}