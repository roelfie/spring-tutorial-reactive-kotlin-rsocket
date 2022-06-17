package com.example.kotlin.chat.service

import com.example.kotlin.chat.mapToViewModel
import com.example.kotlin.chat.repository.MessageRepository
import com.example.kotlin.chat.toDomainObject
import com.example.kotlin.chat.toViewModel
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class PersistentMessageService(val messageRepository: MessageRepository) : MessageService {

    // Notice that List has a built-in .map() function in Kotlin
    override fun latest(): List<MessageVM> =
        messageRepository.findLatest().mapToViewModel()

    override fun after(lastMessageId: String): List<MessageVM> =
        messageRepository.findLatest(lastMessageId).mapToViewModel()

    override fun post(message: MessageVM) {
        messageRepository.save(message.toDomainObject())
    }
}
