package com.paya.domain.tools


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> unAuthorized(msg: String, data: T?): Resource<T> {
            return Resource(Status.UNAUTHORIZED, data, msg)
        }

        fun <T> fatabiToken(msg: String, data: T?): Resource<T> {
            return Resource(Status.FARABITOKEN, data, msg)
        }

        fun <T> idle(data: T?): Resource<T> {
            return Resource(Status.IDLE, data, null)
        }

    }
}
