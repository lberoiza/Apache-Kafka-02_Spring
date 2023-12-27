package lab.spring.kafka.services.consumers;

import lab.spring.kafka.models.PersonData;
import lab.spring.kafka.services.elasticsearch.ElasticSearchPersonDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BatchConsumerService {

  private final ElasticSearchPersonDataService elasticSearchPersonDataService;

  @Autowired
  public BatchConsumerService(ElasticSearchPersonDataService elasticSearchPersonDataService) {
    this.elasticSearchPersonDataService = elasticSearchPersonDataService;
  }

  @KafkaListener(
      id = "${kafka.services.batch.id}",
      autoStartup = "${kafka.services.batch.autoStartup}",
      topics = "${kafka.services.batch.topic}",
      groupId = "${kafka.services.batch.groupId}",
      containerFactory = "listenerBatchContainerFactory",
      properties = {
          "max.poll.interval.ms=4000",
          "max.poll.records=10"
      }
  )
  public void listen(List<ConsumerRecord<String, String>> consumerRecords) {
    log.info("Starting Received Messages");
    for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
      log.info("Batch Consumer: offset={}, partition={}, key={}, value={}", consumerRecord.offset(), consumerRecord.partition(), consumerRecord.key(), consumerRecord.value());
    }
    log.info("Messages Received");
  }


  @KafkaListener(
      id = "${elasticsearch.topic.personData.id}",
      autoStartup = "${kafka.services.batch.autoStartup}",
      topics = "${elasticsearch.topic.personData}",
      groupId = "${elasticsearch.topic.personData.groupId}",
      containerFactory = "listenerBatchContainerFactory",
      properties = {
          "max.poll.interval.ms=4000",
          "max.poll.records=100"
      }
  )
  public void listenPersonDataTopic(List<ConsumerRecord<String, String>> consumerRecords) {
    log.info("Starting Received PersonData Messages");
    for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
      String jsonData = consumerRecord.value();
      Optional<PersonData> personDataOptional = PersonData.fromJson(jsonData);
      personDataOptional.ifPresent(elasticSearchPersonDataService::save);
    }
    log.info("Ending Received PersonData Messages");
  }



}
