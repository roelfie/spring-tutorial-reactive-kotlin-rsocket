package com.example.kotlin.chat.service

import com.example.kotlin.chat.repository.ContentType
import com.example.kotlin.chat.repository.Message
import com.example.kotlin.chat.repository.MessageRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.net.URL

@Service
@Primary
class PersistentMessageService(val messageRepository: MessageRepository) : MessageService {

    // Notice that List has a built-in .map() function in Kotlin

    override fun latest(): List<MessageVM> =
        messageRepository.findLatest().map { domainToView(it) }

    override fun after(lastMessageId: String): List<MessageVM> =
        messageRepository.findLatest(lastMessageId).map { domainToView(it) }

    override fun post(message: MessageVM) {
        messageRepository.save(viewToDomain(message))
    }

    private fun domainToView(message: Message): MessageVM {
        return with(message) {
            MessageVM(
                content,
                UserVM(username, URL(userAvatarImageLink)),
                sent,
                id
            )
        }
    }

    private fun viewToDomain(messageVm: MessageVM): Message {
        return with(messageVm) {
            Message(
                content,
                ContentType.PLAIN,
                sent,
                user.name,
                user.avatarImageLink.toString()
            )
        }
    }
}