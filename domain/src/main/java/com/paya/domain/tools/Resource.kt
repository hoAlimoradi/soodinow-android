package com.paya.domain.tools

import com.paya.domain.models.repo.ExtraRepoInterface


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?,val code:Int,val extra: ExtraRepoInterface? = null) {
    companion object {
        fun <T> success(data: T?,code:Int): Resource<T> {
            return Resource(Status.SUCCESS, data, null,code)
        }

        fun <T> error(msg: String, data: T?, code: Int, extra: ExtraRepoInterface? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg,code,extra)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null,-1)
        }

        fun <T> idle(data: T?): Resource<T> {
            return Resource(Status.IDLE, data, null,-1)
        }

    }
}

