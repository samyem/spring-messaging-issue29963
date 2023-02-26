package com.example.issue29963.service

import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.Message
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Service

@Service
class BatchTest(
    template: KafkaTemplate<String, String>
) {
    init {
        Thread {
            Thread.sleep(3000)
            (0..40).forEachIndexed { _, i ->
                val record = ProducerRecord(
                    if (i < 11) "javaTopic" else "kotlinTopic",
                    "key$i", "kMessage$i"
                )
                record.headers().add("headerInfo", "header$i".toByteArray())
                template.send(record)
            }
        }.start()
    }

    @KafkaListener(topics = ["javaTopic"], groupId = "cg")
    fun javaListListener(records: java.util.List<Message<*>>) {
        println(records)
        println(records[0] is GenericMessage)
    }

    @KafkaListener(topics = ["kotlinTopic"], groupId = "cg")
    fun kafkaListListener(records: List<Message<*>>) {
        println(records)
        println(records[0] is GenericMessage)  // FAIL: Expected to be true, but is false
    }
}