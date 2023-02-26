package com.example.issue29963

import io.github.embeddedkafka.EmbeddedKafka
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Issue29963Application

fun main(args: Array<String>) {
    runApplication<Issue29963Application>(*args)
}
