package com.example.kotlin.chat

import com.example.kotlin.chat.repository.ContentType
import com.example.kotlin.chat.repository.Message
import com.example.kotlin.chat.service.MessageVM
import com.example.kotlin.chat.service.UserVM
import java.net.URL

fun Message.toViewModel(): MessageVM = MessageVM(
    content,
    UserVM(username, URL(userAvatarImageLink)),
    sent,
    id
)

fun MessageVM.toDomainObject(): Message = Message(
    content,
    ContentType.PLAIN,
    sent,
    user.name,
    user.avatarImageLink.toString()
)

fun List<Message>.mapToViewModel(): List<MessageVM> = map { it.toViewModel() }
