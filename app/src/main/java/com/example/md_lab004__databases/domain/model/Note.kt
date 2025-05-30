package com.example.md_lab004__databases.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    @PrimaryKey val id: Int? = null
)
