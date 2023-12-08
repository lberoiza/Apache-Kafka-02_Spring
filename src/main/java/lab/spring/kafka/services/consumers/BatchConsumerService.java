package lab.spring.kafka.services.consumers;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchConsumerService {
  private static final Logger log = org.slf4j.LoggerFactory.getLogger(BatchConsumerService.class);

  @KafkaListener(
      topics = "${kafka.topic.test-topic}",
      groupId = "${kafka.topic.test-groupId}",
      containerFactory = "listenerBatchContainerFactory",
      properties = {
          "max.poll.interval.ms=4000",
          "max.poll.records=10"
      }
  )
  public void listen(List<String> messages) {
    log.info("Starting Received Messages");
    for(String msg : messages) {
      log.info("Batch Consumer: {}", msg);
    }
    log.info("Messages Received");
  }

}
