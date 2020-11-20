package com.paya.domain.models.repo

import java.io.Serializable


data class PercentRepoModel(
	val minimum: Percent,
	val year: Percent,
	val perfect: Percent
): Serializable

data class Percent(
	val percent: Float,
	val price: Long
): Serializable
