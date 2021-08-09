package com.example.clientapp.models

import android.media.Image
import java.io.Serializable
import java.util.*

data class Message(
    var from: String,
    var to: String,
    var text: String,
    var date: String
) : Serializable

data class MessageContainer (
    var chat: List<Message>,
    var talk1: String,
    var talk2: String,
) : Serializable