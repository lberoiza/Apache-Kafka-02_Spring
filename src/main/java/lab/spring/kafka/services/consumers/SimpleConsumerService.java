package lab.spring.kafka.services.consumers;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SimpleConsumerService {
  private static final Logger log = org.slf4j.LoggerFactory.getLogger(SimpleConsumerService.class);

  @KafkaListener(topics = "${kafka.topic.test-topic}", groupId = "${kafka.topic.test-groupId}")
  public void listen(String message) {
    log.info("Received Message in group test-group: {}", message);
  }

}
