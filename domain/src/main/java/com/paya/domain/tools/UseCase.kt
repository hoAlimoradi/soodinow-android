package com.paya.domain.tools

interface UseCase<T,R>{
    suspend fun action(param : T) : Resource<R>
}