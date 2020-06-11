package ru.skillbranch.sbdelivery.http.auth

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class AuthorizedCallAdapterFactory(
    private val callAdapterFactories: List<CallAdapter.Factory>,
    private val authorizedMethods: MutableMap<Int, Boolean>
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val annotation = getAnnotation(annotations)
        for (i in callAdapterFactories.indices) {
            val adapter: CallAdapter<*, *>? = callAdapterFactories[i][returnType, annotations, retrofit]
            if (adapter != null) {
                if (annotation != null) {
                    // get whatever info you need from your annotation
                    return WrappingCallAdapter(adapter, authorizedMethods, true)
                }
                return adapter
            }
        }
        return null
    }

    private fun getAnnotation(annotations: Array<Annotation>): Authorized? {
        for (annotation in annotations) {
            if (Authorized::class.java == annotation.annotationClass) {
                return annotation as Authorized
            }
        }
        return null
    }

}