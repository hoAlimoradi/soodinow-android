package com.example.domain.tools

interface UseCase<T,R>{
    suspend fun action(param : T) : R
}