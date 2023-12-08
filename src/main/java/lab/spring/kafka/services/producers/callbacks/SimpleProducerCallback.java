package lab.spring.kafka.services.producers.callbacks;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;

import java.util.function.BiConsumer;

public class SimpleProducerCallback implements BiConsumer<SendResult<String, String>, Throwable> {
  private static final Logger log = LoggerFactory.getLogger(SimpleProducerCallback.class);

  @Override
  public void accept(SendResult<String, String> sendResult, Throwable throwable) {

    if (throwable != null) {
      log.error("Error while sending message: {}", throwable.getMessage());
      return;
    }

    RecordMetadata recordMetadata = sendResult.getRecordMetadata();
    log.info("Added Topic: {}, Partition: {}, Offset: {}, Key: {}, Value: {}",
        recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(),
        sendResult.getProducerRecord().key(), sendResult.getProducerRecord().value());
  }
}
