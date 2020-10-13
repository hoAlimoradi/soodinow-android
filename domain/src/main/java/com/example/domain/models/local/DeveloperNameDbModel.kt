package com.example.domain.models.local

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DeveloperNameDbModel(
    @Id var id: Long = 0,
    val first: String? = "",
    val last: String? = ""
)