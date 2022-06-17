package com.example.kotlin.chat.service

import com.example.kotlin.chat.asRendered
import com.example.kotlin.chat.mapToViewModel
import com.example.kotlin.chat.repository.MessageRepository
import com.example.kotlin.chat.toDomainObject
import kotlinx.coroutines.flow.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class PersistentMessageService(val messageRepository: MessageRepository) : MessageService {

    val sender: MutableSharedFlow<MessageVM> = MutableSharedFlow()

    // Notice that List has a built-in .map() function in Kotlin
    override fun latest(): Flow<MessageVM> =
        messageRepository.findLatest().mapToViewModel()

    override fun after(lastMessageId: String): Flow<MessageVM> =
        messageRepository.findLatest(lastMessageId).mapToViewModel()

    override fun stream(): Flow<MessageVM> = sender

    override suspend fun post(messages: Flow<MessageVM>) {
        messages.onEach { sender.emit(it.asRendered()) }
            .map { it.toDomainObject() }
            .let { messageRepository.saveAll(it) }
            .collect()
    }
}
