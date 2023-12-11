package lab.spring.kafka.services.consumers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SimpleConsumerService {
  private static final Logger log = org.slf4j.LoggerFactory.getLogger(SimpleConsumerService.class);

  private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

  @Value("${kafka.services.batch.id}")
  private String batchId;

  @Autowired
  public SimpleConsumerService(KafkaListenerEndpointRegistry registry) {
    this.kafkaListenerEndpointRegistry = registry;
  }

  @KafkaListener(topics = "${kafka.services.simple.topic}",
      groupId = "${kafka.services.simple.groupId}",
      containerFactory = "listenerContainerFactory")
  public void listen(String message) {
    log.info("Simple Consumer Message: {}", message);
    try {
      analyzeMessage(message);
    }catch (NullPointerException e){
      log.error("Consumer with Id: '{}' not found", batchId);
    }
  }

  private void analyzeMessage(String message) throws NullPointerException {
    switch (message) {
      case "enableBatch":
        Objects.requireNonNull(kafkaListenerEndpointRegistry.getListenerContainer(batchId)).start();
        break;
      case "disableBatch":
        Objects.requireNonNull(kafkaListenerEndpointRegistry.getListenerContainer(batchId)).stop();
    }
  }

}
