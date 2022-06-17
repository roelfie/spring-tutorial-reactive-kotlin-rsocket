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

## Refactoring to a non-blocking web service implementation with coroutines

The starting point for this refactoring is commit 
[5f22abba57e27acac8fa3cc141ae0590cfed4645](https://github.com/roelfie/spring-tutorial-reactive-kotlin-rsocket/commit/5f22abba57e27acac8fa3cc141ae0590cfed4645):
* dependencies:
  * Spring lib: `spring-boot-starter-data-jdbc` --> `spring-boot-starter-data-r2dbc`.
  * H2 driver: `com.h2database:h2` --> `io.r2dbc:r2dbc-h2`.
* Applied `suspend` to all methods (all the way down from `@Controller` / `@RestController` to `MessageRepository`).
* All tests wrapped in `runBlocking { ... }` coroutine builders.
* For the `@[Rest]Controller`s it is apparently sufficient to simply `suspend` to its methods. Spring WebFlux 
  handles it for us.

The use of coroutines has made this implementation non-blocking (retrieving items from the database is no longer 
blocking the calling thread). 

But notice that this solution does not use reactive streams: The repository and the 
@RestContoller still return a `List<Message>`.

