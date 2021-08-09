package com.example.clientapp.models

import ge.dgoginashvili_gkalandadze.finalproject.dataModel.UserData
import java.io.Serializable

data class UserChat(
    var chats: List<String>
) : Serializable