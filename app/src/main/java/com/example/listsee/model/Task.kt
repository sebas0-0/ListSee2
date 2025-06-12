package com.example.listsee.model

import java.util.Date

data class Task(
                val id: String,
                val name: String,
                val description: String,
                val date: Date,
                val userId: String
)


