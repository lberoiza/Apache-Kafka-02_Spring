package lab.spring.kafka.services.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Service
public class SimpleProducerService {

  @Value("${kafka.services.batch.topic}")
  private String batchTopic;

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
