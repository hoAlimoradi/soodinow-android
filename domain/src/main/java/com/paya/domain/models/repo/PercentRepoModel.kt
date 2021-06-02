package com.paya.domain.models.repo

import java.io.Serializable



data class PercentEfficiency(
	val title: String,
	val percent: Float,
	val price: Float
) : Serializable
