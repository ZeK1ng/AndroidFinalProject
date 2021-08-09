package com.example.clientapp.models

import android.media.Image
import java.io.Serializable
import java.util.*

data class Message(
    var from: String = "",
    var to: String = "",
    var text: String = "",
    var date: String = ""
) : Serializable {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "from" to from,
            "to" to to,
            "text" to text,
            "date" to date
        )
    }
}

data class MessageContainer (
    var chat: List<Message> = listOf(),
    var talk1: String = "",
    var talk2: String = "",
) : Serializable {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "chat" to chat,
            "talk1" to talk1,
            "talk2" to talk2
        )
    }
}