package lab.spring.kafka.services.metrics;

import lab.spring.kafka.factories.PersonDataFactory;
import lab.spring.kafka.services.producers.SimpleProducerService;
import lab.spring.kafka.models.PersonData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(properties = "kafka.bootstrap_servers_config=${spring.embedded.kafka.brokers}")
@EmbeddedKafka
class MeterRegistryServerTest {

  @Autowired
  SimpleProducerService simpleProducerService;

  @Autowired
  MeterRegistryServer meterRegistryServer;

  @Value("${test.meterregistryserver.topic}")
  private String simpleTestTopic;



  @Test
  void sendMessage() throws ExecutionException, InterruptedException {

    int nrOfMessages = 20;

    for(int i = 0; i < nrOfMessages; i++){
      PersonData personData = PersonDataFactory.createPersonData();
      log.info("Sending message nr {}: {} to topic {}", i+1, personData.toJson(), simpleTestTopic);
      simpleProducerService.sendMessage(simpleTestTopic, "same-key-for-all", personData.toJson()).get();
    }
    assertEquals(nrOfMessages, (int) meterRegistryServer.getTotalMessages());
  }

}























