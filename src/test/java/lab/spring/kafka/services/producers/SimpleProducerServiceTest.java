package lab.spring.kafka.services.producers;

import lab.spring.kafka.factories.PersonDataFactory;
import lab.spring.kafka.testmodels.PersonData;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SimpleProducerServiceTest {

  @Autowired
  SimpleProducerService simpleProducerService;

  @Value("${test.simpleproducerservice.topic}")
  private String simpleTestTopic;


  @Test
  void sendMessage() throws ExecutionException, InterruptedException {

    PersonData personData = PersonDataFactory.createPersonData();
    ProducerRecord<String, String> producerRecord = simpleProducerService
        .sendMessage(simpleTestTopic, personData.getFirstName(), personData.toJson())
        .get()
        .getProducerRecord();


    assertEquals(simpleTestTopic, producerRecord.topic());
    assertEquals(personData.toJson(), producerRecord.value());
    assertEquals(personData.getFirstName(), producerRecord.key());

  }


  @Test
  void sendMessageCallback() {

    PersonData personData = PersonDataFactory.createPersonData();
    simpleProducerService
        .sendMessageWithCallback(
            simpleTestTopic,
            personData.getFirstName(),
            personData.toJson(),
            (result, error) -> {
              assertNull(error);
              assertNotNull(result);
              assertEquals(simpleTestTopic, result.getRecordMetadata().topic());
              assertEquals(personData.getFirstName(), result.getProducerRecord().key());
              assertEquals(personData.toJson(), result.getProducerRecord().value());
            });
  }


}