# Spring tutorial: Spring Boot with Kotlin Coroutines and RSocket

This is a fork of the [kotlin-spring-chat](https://github.com/kotlin-hands-on/kotlin-spring-chat) repository 
belonging to the Spring tutorial 
[Spring Boot with Kotlin Coroutines and RSocket](https://spring.io/guides/tutorials/spring-webflux-kotlin-rsocket/).

Original documentation moved to [TUTORIAL.adoc](./TUTORIAL.adoc).

## Kotlin

I learned some new Kotlin features...

### TODO(reason: String)

This standard Kotlin function always throws a `NotImplementedError`.

```kotlin
fun post(message: Message) {
    TODO("Not yet implemented")
}
```

### Spring Autowiring at field level

```kotlin
@Autowired
lateinit var client: TestRestTemplate
```
