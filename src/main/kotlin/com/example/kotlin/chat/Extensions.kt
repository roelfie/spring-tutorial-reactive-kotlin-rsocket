package com.example.kotlin.chat

import com.example.kotlin.chat.repository.ContentType
import com.example.kotlin.chat.repository.ContentType.MARKDOWN
import com.example.kotlin.chat.repository.Message
import com.example.kotlin.chat.service.MessageVM
import com.example.kotlin.chat.service.UserVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import java.net.URL

fun Message.toViewModel(): MessageVM = MessageVM(
    contentType.render(content),
    UserVM(username, URL(userAvatarImageLink)),
    sent,
    id
)

fun MessageVM.toDomainObject(contentType: ContentType = MARKDOWN): Message = Message(
    content,
    contentType,
    sent,
    user.name,
    user.avatarImageLink.toString()
)

fun Flow<Message>.mapToViewModel(): Flow<MessageVM> = map {  it.toViewModel() }

fun MessageVM.asRendered(contentType: ContentType = MARKDOWN): MessageVM =
    this.copy(content = contentType.render(this.content))

fun ContentType.render(content: String): String = when(this) {
    ContentType.PLAIN -> content
    MARKDOWN -> {
        val flavour = CommonMarkFlavourDescriptor()
        HtmlGenerator(
            content,
            MarkdownParser(flavour).buildMarkdownTreeFromString(content),
            flavour
        ).generateHtml()
    }
}