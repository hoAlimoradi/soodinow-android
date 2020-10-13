package com.example.common

interface BiMapper<T, R> {

    fun mapTo(item: T): R

    fun mapBack(item: R): T
}