package com.paya.common

interface BiMapper<T, R> {

    fun mapTo(item: T): R

    fun mapBack(item: R): T
}