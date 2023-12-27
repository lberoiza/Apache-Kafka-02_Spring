package lab.spring.kafka.services.producers;

import lab.spring.kafka.models.PersonData;
import lab.spring.kafka.utils.datafactories.PersonDataFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Slf4j
@Service
public class SimpleProducerService {

  @Value("${kafka.services.batch.topic}")
  private String batchTopic;

  @Value("${elasticsearch.topic.personData}")
  private String personDataTopic;

  @Value("${elasticsearch.topic.personData.nrTestData}")
  private int personDataNrTestData;

  @Value("${kafka.service.batch.scheduleMessages.enabled}")
  private boolean scheduleMessagesEnabled;


  private final KafkaTemplate<String, String> kafkaProducer;

  @Autowired
  public SimpleProducerService(KafkaTemplate<String, String> kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }


  public CompletableFuture<SendResult<String, String>> sendMessage(String topic, String key, String message) {
    return kafkaProducer.send(topic, key, message);
  }

  public CompletableFuture<SendResult<String, String>> sendMessage(String topic, String message) {
    return kafkaProducer.send(topic, message);
  }

  public void sendMessageWithCallback(String topic, String message, BiConsumer<? super SendResult<String, String>,
      ? super Throwable> lambda) {
    CompletableFuture<SendResult<String, String>> futureResult = sendMessage(topic, message);
    futureResult.whenComplete(lambda);
  }

  public void sendMessageWithCallback(String topic, String key, String message, BiConsumer<? super SendResult<String, String>,
      ? super Throwable> lambda) {
    CompletableFuture<SendResult<String, String>> futureResult = sendMessage(topic, message);
    futureResult.whenComplete(lambda);
  }


  public void createPersonTestData() {
    log.info("Starting Process Sending PersonData to Topic: {}", personDataTopic);
    for (int i = 0; i < personDataNrTestData; i++) {
      PersonData personData = PersonDataFactory.createPersonData();
      personData.setId(UUID.randomUUID().toString());
      kafkaProducer.send(personDataTopic, "personDataTest", personData.toJson());
    }
    log.info("Ending Process Sending PersonData to Topic: {}", personDataTopic);
  }

  public void sendSimpleMessage() {
    // Envia 100 mensajes
    if (scheduleMessagesEnabled) {
      sendTestData(batchTopic, "key", "message nr", 100);
    }
  }


  private void sendTestData(String topic, String key, String message, int nrOfMessages) {
    for (int i = 0; i < nrOfMessages; i++) {
      kafkaProducer.send(topic, key + i, String.format("%s-%d", message, i));
    }
  }


}
