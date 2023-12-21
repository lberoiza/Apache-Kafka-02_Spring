package lab.spring.kafka.services.metrics;

import lab.spring.kafka.factories.PersonDataFactory;
import lab.spring.kafka.services.producers.SimpleProducerService;
import lab.spring.kafka.testmodels.PersonData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MeterRegistryServerTest {

  @Autowired
  SimpleProducerService simpleProducerService;

  @Autowired
  MeterRegistryServer meterRegistryServer;

  @Value("${test.meterregistryserver.topic}")
  private String simpleTestTopic;



  @Test
  void sendMessage(){

    int nrOfMessages = 10;

    for(int i = 0; i < nrOfMessages; i++){
      PersonData personData = PersonDataFactory.createPersonData();
      log.info("Sending message: {}", personData.toJson());
      simpleProducerService.sendMessage(simpleTestTopic, "same-key-for-all", personData.toJson());
    }
    assertEquals(nrOfMessages, (int) meterRegistryServer.getTotalMessages());
  }

}























