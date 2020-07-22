package retrofit2

import java.util.concurrent.Executor

object Retrofit2Platform {

    fun defaultCallAdapterFactories(executor: Executor?): List<CallAdapter.Factory> {
        var mExecutor: Executor? = executor
        if (mExecutor == null) {
            mExecutor = defaultCallbackExecutor()
        }
        return Platform.get().defaultCallAdapterFactories(mExecutor)
    }

    fun defaultCallbackExecutor(): Executor? {
        return Platform.get().defaultCallbackExecutor()
    }

}